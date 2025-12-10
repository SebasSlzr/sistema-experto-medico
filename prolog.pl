% ============================================
% SISTEMA EXPERTO - DIAGNOSTICO MEDICO
% ============================================

% Predicado dinamico - los hechos se cargan desde MySQL
:- dynamic(enfermedad/4).
% enfermedad(Nombre, ListaSintomas, Categoria, Recomendacion)


% ============================================
% REGLA: pertenece/2
% Verifica si un elemento esta en una lista
% ============================================
pertenece(X, [X|_]).
pertenece(X, [_|Resto]) :-
    pertenece(X, Resto).


% ============================================
% REGLA: coincide_sintomas/2
% Verdadero si TODOS los sintomas del usuario
% estan en la lista de sintomas de la enfermedad
% ============================================
coincide_sintomas([], _).
coincide_sintomas([Sintoma|Resto], SintomasEnfermedad) :-
    pertenece(Sintoma, SintomasEnfermedad),
    coincide_sintomas(Resto, SintomasEnfermedad).


% ============================================
% REGLA: tiene_sintoma_comun/2
% Verdadero si hay al menos UN sintoma en comun
% ============================================
tiene_sintoma_comun([Sintoma|_], SintomasEnfermedad) :-
    pertenece(Sintoma, SintomasEnfermedad).
tiene_sintoma_comun([_|Resto], SintomasEnfermedad) :-
    tiene_sintoma_comun(Resto, SintomasEnfermedad).


% ============================================
% REGLA: diagnostico/2
% Devuelve enfermedad con al menos un sintoma comun
% ============================================
diagnostico(SintomasUsuario, Enfermedad) :-
    enfermedad(Enfermedad, SintomasEnfermedad, _, _),
    tiene_sintoma_comun(SintomasUsuario, SintomasEnfermedad).


% ============================================
% REGLA: diagnostico_estricto/2
% Devuelve enfermedad donde TODOS los sintomas coinciden
% ============================================
diagnostico_estricto(SintomasUsuario, Enfermedad) :-
    enfermedad(Enfermedad, SintomasEnfermedad, _, _),
    coincide_sintomas(SintomasUsuario, SintomasEnfermedad).


% ============================================
% REGLA: diagnostico_categoria/3
% Diagnostico filtrado por categoria
% ============================================
diagnostico_categoria(SintomasUsuario, Categoria, Enfermedad) :-
    enfermedad(Enfermedad, SintomasEnfermedad, Categoria, _),
    tiene_sintoma_comun(SintomasUsuario, SintomasEnfermedad).


% ============================================
% REGLA: recomendacion/2
% Obtiene la recomendacion de una enfermedad
% ============================================
recomendacion(Enfermedad, Recomendacion) :-
    enfermedad(Enfermedad, _, _, Recomendacion).


% ============================================
% REGLA: categoria/2
% Obtiene la categoria de una enfermedad
% ============================================
categoria(Enfermedad, Categoria) :-
    enfermedad(Enfermedad, _, Categoria, _).


% ============================================
% REGLA: sintomas_de/2
% Obtiene los sintomas de una enfermedad
% ============================================
sintomas_de(Enfermedad, Sintomas) :-
    enfermedad(Enfermedad, Sintomas, _, _).


% ============================================
% REGLA: enfermedades_por_sintoma/2
% Encuentra enfermedades que tengan un sintoma especifico
% Usa recursion en lugar de findall
% ============================================
enfermedades_por_sintoma(Sintoma, Enfermedad) :-
    enfermedad(Enfermedad, Sintomas, _, _),
    pertenece(Sintoma, Sintomas).


% ============================================
% REGLA: es_cronica/1
% Verifica si una enfermedad es cronica
% ============================================
es_cronica(Enfermedad) :-
    enfermedad(Enfermedad, _, cronica, _).


% ============================================
% REGLA: es_viral/1
% Verifica si una enfermedad es viral
% ============================================
es_viral(Enfermedad) :-
    enfermedad(Enfermedad, _, viral, _).


% ============================================
% REGLA: contar_coincidencias/3
% Cuenta cuantos sintomas del usuario coinciden
% ============================================
contar_coincidencias([], _, 0).
contar_coincidencias([Sintoma|Resto], SintomasEnf, Contador) :-
    pertenece(Sintoma, SintomasEnf),
    contar_coincidencias(Resto, SintomasEnf, ContadorResto),
    Contador is ContadorResto + 1.
contar_coincidencias([Sintoma|Resto], SintomasEnf, Contador) :-
    \+ pertenece(Sintoma, SintomasEnf),
    contar_coincidencias(Resto, SintomasEnf, Contador).


% ============================================
% REGLA: longitud/2
% Calcula la longitud de una lista
% ============================================
longitud([], 0).
longitud([_|Resto], N) :-
    longitud(Resto, N1),
    N is N1 + 1.