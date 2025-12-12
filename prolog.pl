
% Reglas estaticas


% Predicado dinamico - los hechos se cargan desde MySQL
:- dynamic(enfermedad/4).
% enfermedad(Nombre, ListaSintomas, Categoria, Recomendacion)



% REGLA: pertenece/2
% Verifica si un elemento esta en una lista
pertenece(X, [X|_]).
pertenece(X, [_|Resto]) :-
    pertenece(X, Resto).



% REGLA: tiene_sintoma_comun/2
% Verdadero si hay al menos UN sintoma en comun
tiene_sintoma_comun([Sintoma|_], SintomasEnfermedad) :-
    pertenece(Sintoma, SintomasEnfermedad).
tiene_sintoma_comun([_|Resto], SintomasEnfermedad) :-
    tiene_sintoma_comun(Resto, SintomasEnfermedad).



% REGLA: diagnostico/2
% Devuelve enfermedad con al menos un sintoma comun
diagnostico(SintomasUsuario, Enfermedad) :-
    enfermedad(Enfermedad, SintomasEnfermedad, _, _),
    tiene_sintoma_comun(SintomasUsuario, SintomasEnfermedad).



% REGLA: diagnostico_categoria/3
% Diagnostico filtrado por categoria
diagnostico_categoria(SintomasUsuario, Categoria, Enfermedad) :-
    enfermedad(Enfermedad, SintomasEnfermedad, Categoria, _),
    tiene_sintoma_comun(SintomasUsuario, SintomasEnfermedad).



% REGLA: recomendacion/2
% Obtiene la recomendacion de una enfermedad
recomendacion(Enfermedad, Recomendacion) :-
    enfermedad(Enfermedad, _, _, Recomendacion).



% REGLA: categoria/2
% Obtiene la categoria de una enfermedad
categoria(Enfermedad, Categoria) :-
    enfermedad(Enfermedad, _, Categoria, _).



% REGLA: enfermedades_por_sintoma/2
% Encuentra enfermedades que tengan un sintoma especifico
% Usa recursion en lugar de findall
enfermedades_por_sintoma(Sintoma, Enfermedad) :-
    enfermedad(Enfermedad, Sintomas, _, _),
    pertenece(Sintoma, Sintomas).



% REGLA: enfermedades_cronicas/1
% Lista todas las enfermedades cronicas
enfermedades_cronicas(Enfermedad) :-
    enfermedad(Enfermedad, _, cronica, _).



