(* Author : Sourabh Soni
   Emain  : ssoni2@buffalo.edu
   Date   : 12/05/2013
   Descr  : Fibonacci Servers. Generating fibonacci numbers using multiple servers. Example to show case threads, 		channels and asynchronous communication protocol.*)

CM.make "$cml/cml.cm";
open CML;

val chan1: int chan = channel();
val chan2: int chan = channel();
val chan3: int chan = channel();

fun sender1 n 0 = ()
  | sender1 n m = let
	val ig = send(chan1, n);
	val re =  recv(chan2)
	in
	sender1 (re) (m-1)
end; 

fun sender2 n = let
	val re = recv(chan1);
	val sum = re + n 
	in
        send (chan3, re); send(chan2, n); sender2 sum
end;  

fun receiver () = let
	val i = recv(chan3); 
	in 
	TextIO.print(Int.toString(i)^"\n");
	receiver ()	
end;	

fun main () = let
	in 
	spawn (fn () => sender1 0 50);
	spawn (fn () => sender2 1);
	spawn (fn () => receiver ());
	()
	
end;

RunCML.doit(main,NONE);


