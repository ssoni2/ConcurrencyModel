(* Author : Sourabh Soni
   Emain  : ssoni2@buffalo.edu
   Date   : 12/05/2013
   Descr  : Simple Servers. Sender sends natural number and Receiver recieve them and print*)

CM.make "$cml/cml.cm";
open CML;

val chan: int chan = channel();

fun sender 100 = send(chan, 100)
  | sender n = let
	in
	send(chan, n); sender (n+1)
end; 
  
  

fun receiver () = let
	val i = recv(chan); 
	in 
	TextIO.print(Int.toString(i)^"\n");
	receiver ()	
end;	

fun main () = let
	in 
	spawn (fn () => sender 0);
	spawn (fn () => receiver ());
	()
	
end;

RunCML.doit(main,NONE);


