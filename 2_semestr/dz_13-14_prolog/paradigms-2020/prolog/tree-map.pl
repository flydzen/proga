make(Key, Value, R) :- 
	rand_int(88005553535, P),
	R = node(Key, Value, P, null, null).

split(null, _, null, null) :- !.
split(node(K, V, P, L, R), Key, RLeft, RRight) :- Key >= K,
	split(R, Key, T1, RRight),
	RLeft = node(K, V, P, L, T1), !.
split(node(K, V, P, L, R), Key, RLeft, RRight) :- 
	split(L, Key, RLeft, T2),
	RRight = node(K, V, P, T2, R).

merge(M, null, M) :- !.
merge(null, M, M) :- !.
merge(node(Kl, Vl, Pl, Ll, Rl), node(Kr, Vr, Pr, Lr, Rr),  R) :-
	Pl > Pr,
	merge(Rl, node(Kr, Vr, Pr, Lr, Rr), T),
	R = node(Kl, Vl, Pl, Ll, T), !.
merge(node(Kl, Vl, Pl, Ll, Rl), node(Kr, Vr, Pr, Lr, Rr),  R) :-
	merge(node(Kl, Vl, Pl, Ll, Rl), Lr, T),
	R = node(Kr, Vr, Pr, T, Rr).

map_build([], null) :- !.
map_build([(K, V) | T], R) :- map_build(T, RT), map_put(RT, K, V, R).

cut(Map, Key, L, R) :- 
	KM1 is Key - 1,
	split(Map, KM1, L, T2),
	split(T2, Key, T3, R).

map_put(Map, Key, Value, R):-
	make(Key, Value, New),
	cut(Map, Key, LT, RT),
	merge(LT, New, R1),
	merge(R1, RT, R).

map_get(node(K, V, _, _, _), K, V) :- !.
map_get(node(K, V, P, L, R), Key, Value) :-
	K > Key, map_get(L, Key, Value).
map_get(node(K, V, P, L, R), Key, Value) :-
	K < Key, map_get(R, Key, Value).

map_remove(Map, Key, R) :- 
	cut(Map, Key, Left, Right),
	merge(Left, Right, R).

min(node(K, V, P, L, R), C) :- L == null, C = K.
min(node(K, V, P, L, R), C) :- L \= null, min(L, C).
max(node(K, V, P, L, R), C) :- R == null, C = K.
max(node(K, V, P, L, R), C) :- R \= null, max(R, C).

map_ceilingKey(Map, Key, CeilingKey) :-
	KM1 is Key - 1,
	split(Map, KM1, L, R),
	min(R, CeilingKey).

