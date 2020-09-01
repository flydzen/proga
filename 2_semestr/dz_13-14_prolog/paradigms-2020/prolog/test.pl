eratFor(N, V, Max) :-
	N =< Max,
	NV is N + V,
	assert(composite(N)),
	eratFor(NV, V, Max).
erat(N, Max) :-
	N =< Max,
	not composite(N),
	NN is N + 1,
	NV is N + N,
	eratFor(NV, N, Max),
	erat(NN, Max), !.
erat(N, Max) :-
	N =< Max,
	NN is N + 1,
	erat(NN, Max).
composite(1).
prime(N) :-
	not composite(N).
init(N) :- erat(2, N).
sorted([H]) :- H > 1.
sorted([H1, H2 | T]) :- H1 =< H2.
prime_divisors(N, L) :- nonvar(L), prime_divisors(N, 1, L).
prime_divisors(N, L) :- var(L), prime_divisorsC(N, 2, L).
prime_divisors(Max, S, []) :- Max = S.
prime_divisors(Max, S, [H | T]) :-
	prime(H),
	M is H * S,
	sorted([H | T]),
	prime_divisors(Max, M, T).
min_prime(N, S, R) :-
	prime(S),
	mod(N, S) =:= 0,
	R is S, !.
min_prime(N, S, R) :- 
	S * S =< N,
	SS is S + 1, !,
	min_prime(N, SS, R), !.
min_prime(N, S, R) :-
	S * S > N,
	R is N.
prime_divisorsC(1, _, []) :- !.
prime_divisorsC(N, L, [H | T]) :-
	min_prime(N, L, H),
	NN is div(N, H),
	prime_divisorsC(NN, H, T).

toLow(N, K, [R]) :- N < K, R is N, !.
toLow(N, K, [H | T]) :-
	N >= K,
	D is div(N, K),
	H is mod(N, K),
	toLow(D, K, T).

prime_palindrome(N, K) :- 
	prime(N),
	toLow(N, K, L),
	reverse(L, R),
	L = R.
