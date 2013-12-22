(* Author : Sourabh Soni
   Emain  : ssoni2@buffalo.edu
   Date   : 11/08/2013
   Descr  : Meta Language to create infinite list and print
		Fibonacci List
		Even Fibonacci List
		Odd Fibonacci List
		Pair of even & odd Fibonacci List *)

datatype 'a inflist = NIL
                    | CONS of 'a * (unit -> 'a inflist);

fun HD (CONS(a,b)) = a
  | HD NIL = raise Subscript;

fun TL (CONS(a,b)) = b()
  | TL NIL = raise Subscript;

fun NULL NIL = true
  | NULL _ = false;

fun FILTER f l = if NULL l
                 then NIL
                 else if f (HD l)
                      then CONS(HD l, fn () => 
                                         (FILTER f (TL l)))
                      else FILTER f (TL l);


fun TAKE(xs, 0) = []
  | TAKE(NIL, n) = raise Subscript
  | TAKE(CONS(x,xf), n) = x::TAKE(xf(), n-1);

fun even 0 = true 
  | even n = odd (n-1) 
 and 
    odd 0 = false 
  | odd n = even (n-1);

fun fib n m = CONS(n,fn () => fib m (n+m)); 

val fibs = fib 0 1; 

val evenFibs = FILTER (fn x => even x) (fibs);  

val oddFibs  = FILTER (fn x => odd x) (fibs); 

fun printGenList f nil = ()
  | printGenList f (h::t) = let val k = f(h)
				in printGenList f t
				end;

fun printList nil = ()
  | printList l = printGenList (fn i => print(Int.toString(i)^" ")) l;

fun printPairList nil = ()
  | printPairList l = printGenList (fn(i, j) => print("("^Int.toString(i)^", "^Int.toString(j)^") ")) l;

fun ZIP l  = case l of (CONS(x,xf), CONS(y,yf)) => CONS((x, y), fn () => ZIP(xf(), yf()))
		| (NIL, NIL) => raise Subscript
		| (NIL, _) => raise Subscript
		| (_, NIL) => raise Subscript;			    
                      
printList(TAKE (fibs, 20));
printList(TAKE (evenFibs, 10));
printList(TAKE (oddFibs, 10));
printPairList(TAKE (ZIP(evenFibs, oddFibs), 10));