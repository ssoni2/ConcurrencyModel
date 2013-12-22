(* Author : Sourabh Soni
   Emain  : ssoni2@buffalo.edu
   Date   : 12/05/2013
   Descr  : Events and Mailboxes. In this CML code we have creating a small mailbox abstraction.  Our mailbox will be 		parameterized by two channels, an in channel and an out channel.  The mailbox will receive on the in 		channel and buffer the values in a list the mailbox maintains. Other threads may consume values from the 		mailbox by receiving from the out channel. *)

CM.make "$cml/cml.cm";
open CML;

fun mailbox inCh outCh list =  
	(
		if null list then
		(
			mailbox inCh outCh [recv(inCh)]		
		)
		else (
			select [
				wrap (recvEvt inCh, fn y => (mailbox inCh outCh (list@[y]))),
				wrap (sendEvt(outCh, hd list), fn () => (mailbox inCh outCh (tl list)))
			]
		)
	)

fun receiver outCh = 
	let
		val newVal = recv(outCh)
	in
	TextIO.print(Int.toString(newVal) ^ "\n");
	receiver outCh
end
fun sender inCh num = 
	(
	if num <> 0 then (
		send(inCh, num);
		sender inCh (num-1)
	)
	else (
		send(inCh, num)
	)	
	);
fun generator inCh outCh num = 
	if num = 0 then inCh
	else (
		let 
			val newCh = channel()
		in (
			spawn(fn () => mailbox inCh outCh nil ); 
			generator outCh newCh (num-1)
		)
		end
	);
fun main () =
  let val chStart = channel()
	val chEnd = generator chStart (channel()) 100
	val _ = spawn (fn () => receiver chEnd)
	val _ = spawn (fn () => ignore (sender chStart 50))
	val _ = spawn (fn () => ignore (sender chStart 50)) 
  in ()
  end;
RunCML.doit(main, NONE);