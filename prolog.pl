% ============================================
% SISTEMA EXPERTO DE DIAGNOSTICO MEDICO
% Motor de inferencia - Reglas
% ============================================

% Declarar predicado dinamico (los hechos se cargan desde MySQL)
:- dynamic(enfermedad/4).

% enfermedad(Nombre, ListaSintomas, Categoria, Recomendacion)
% Estos hechos se cargan dinamicamente con assertz desde Java

% ============================================
% REGLAS DE DIAGNOSTICO
% ============================================

% diagnostico(SintomasUsuario, Enfermedad)
% Encuentra enfermedades que tengan al menos un sintoma en comun
diagnostico(SintomasUsuario, Enfermedad) :-
    enfermedad(Enfermedad, SintomasEnfermedad, _, _),
    tiene_sintoma_comun(SintomasUsuario, SintomasEnfermedad).

% tiene_sintoma_comun(ListaUsuario, ListaEnfermedad)
% Verdadero si hay al menos un sintoma en comun (usa recursion)
tiene_sintoma_comun([Sintoma|_], SintomasEnfermedad) :-
    pertenece(Sintoma, SintomasEnfermedad).
tiene_sintoma_comun([_|Resto], SintomasEnfermedad) :-
    tiene_sintoma_comun(Resto, SintomasEnfermedad).

% pertenece(Elemento, Lista)
% Verifica si un elemento pertenece a una lista (recursion)
pertenece(X, [X|_]).
pertenece(X, [_|Resto]) :-
    pertenece(X, Resto).

% ============================================
% REGLAS DE CONSULTA
% ============================================

% recomendacion(Enfermedad, Recomendacion)
% Obtiene la recomendacion de una enfermedad
recomendacion(Enfermedad, Recomendacion) :-
    enfermedad(Enfermedad, _, _, Recomendacion).

% categoria(Enfermedad, Categoria)
% Obtiene la categoria de una enfermedad
categoria(Enfermedad, Categoria) :-
    enfermedad(Enfermedad, _, Categoria, _).

% sintomas_de(Enfermedad, Sintomas)
% Obtiene los sintomas de una enfermedad
sintomas_de(Enfermedad, Sintomas) :-
    enfermedad(Enfermedad, Sintomas, _, _).

% ============================================
% REGLAS ADICIONALES CON RECURSION
% ============================================

% contar_sintomas_comunes(SintomasUsuario, SintomasEnfermedad, Contador)
% Cuenta cuantos sintomas coinciden (recursion)
contar_sintomas_comunes([], _, 0).
contar_sintomas_comunes([Sintoma|Resto], SintomasEnfermedad, Contador) :-
    pertenece(Sintoma, SintomasEnfermedad),
    contar_sintomas_comunes(Resto, SintomasEnfermedad, ContadorResto),
    Contador is ContadorResto + 1.
contar_sintomas_comunes([Sintoma|Resto], SintomasEnfermedad, Contador) :-
    \+ pertenece(Sintoma, SintomasEnfermedad),
    contar_sintomas_comunes(Resto, SintomasEnfermedad, Contador).

% enfermedades_cronicas(Lista)
% Obtiene enfermedades cronicas usando recursion
enfermedades_cronicas(Lista) :-
    obtener_por_categoria(cronica, Lista).

% obtener_por_categoria(Categoria, Lista)
% Obtiene todas las enfermedades de una categoria
obtener_por_categoria(Categoria, Lista) :-
    obtener_por_categoria_aux(Categoria, [], Lista).

obtener_por_categoria_aux(Categoria, Acumulador, Lista) :-
    enfermedad(Nombre, _, Categoria, _),
    \+ pertenece(Nombre, Acumulador),
    obtener_por_categoria_aux(Categoria, [Nombre|Acumulador], Lista).
obtener_por_categoria_aux(_, Lista, Lista).