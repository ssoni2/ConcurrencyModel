/*
 * ssoni2@buffalo.edu
 * 50096364
 */

class MyRunnable implements Runnable {
	CDLCoarseRW<String> list;

	public MyRunnable(CDLCoarseRW<String> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
	}

	public void insert() throws Exception {
		// final int i =10;
		CDLCoarseRW<String>.Cursor cursor;
		cursor = list.reader(list.head());
		cursor.writer().insertAfter(
				Thread.currentThread().getName() + " Initial-1");
		cursor.next();
		cursor.writer().insertAfter(
				Thread.currentThread().getName() + " Initial-2");
		cursor.next();
		cursor.writer().insertAfter(
				Thread.currentThread().getName() + " Initial-3");

	}

	public void run1() throws Exception {
		CDLCoarseRW<String>.Cursor cursor;
		cursor = list.reader(list.head());
		cursor.writer().insertAfter(
				Thread.currentThread().getName() + " After1");
		cursor.next();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cursor.writer().insertBefore(
				Thread.currentThread().getName() + "Before1");
		cursor.writer().insertAfter(
				Thread.currentThread().getName() + " After2");
		cursor.writer().insertBefore(
				Thread.currentThread().getName() + "Before2");
		cursor.writer().insertAfter(
				Thread.currentThread().getName() + " After3");
		cursor.next();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cursor.writer().insertBefore(
				Thread.currentThread().getName() + "Before3");
		cursor.next();
		cursor.writer().insertAfter(
				Thread.currentThread().getName() + " After4");
		cursor.next();
		cursor.writer().insertAfter(
				Thread.currentThread().getName() + " After5");
		// list.display();
		// cursor.writer().delete();
		// cursor.writer().delete();
		// list.display();
		cursor = list.reader(list.head());
		cursor.next();
		cursor.next();
		cursor.writer().delete();
		// list.display();
		cursor = list.reader(list.head());
		cursor.next();
		cursor.writer().delete();
		// list.display();
		cursor = list.reader(list.head());
		cursor.next();
		// cursor.writer().delete();
		// list.display();
		cursor = list.reader(list.head());
		cursor.writer().insertBefore(
				Thread.currentThread().getName() + "Before4");
		cursor.writer().insertBefore(
				Thread.currentThread().getName() + "Before5");
		cursor.previous();
		cursor.previous();
		cursor.writer().insertBefore(
				Thread.currentThread().getName() + "Before6");
		// list.display();
		// cursor.writer().delete();
		// cursor.writer().delete();
		// list.display();
		System.out.println(Thread.currentThread().getName() + " --- Done");

	}

	public void run2() throws Exception {
		CDLCoarseRW<String>.Cursor cursor;
		cursor = list.reader(list.head());
		if (Thread.currentThread().getName().equalsIgnoreCase("Thread-1")) {
			cursor.next();
		} else if (Thread.currentThread().getName()
				.equalsIgnoreCase("Thread-2")) {
			cursor.next();
			cursor.next();
		} else if (Thread.currentThread().getName()
				.equalsIgnoreCase("Thread-3")) {
			cursor.next();
			cursor.next();
			cursor.next();
		}
		System.out.println(Thread.currentThread().getName() + "-"
				+ cursor.current.value());
		cursor.writer().insertBefore(
				Thread.currentThread().getName() + "Before-0");

	}

	public void run3() throws Exception {
		CDLCoarseRW<String>.Cursor cursor;
		cursor = list.reader(list.head());
		if (Thread.currentThread().getName().equalsIgnoreCase("Thread-1")) {
			cursor.next();
		} else if (Thread.currentThread().getName()
				.equalsIgnoreCase("Thread-2")) {
			cursor.next();
			cursor.next();
		} else if (Thread.currentThread().getName()
				.equalsIgnoreCase("Thread-3")) {
			cursor.next();
			cursor.next();
			cursor.next();
		}
		System.out.println(Thread.currentThread().getName() + "-"
				+ cursor.current.value());
		cursor.writer().insertAfter(
				Thread.currentThread().getName() + "After-0");

	}

	public void run4() throws Exception {
		CDLCoarseRW<String>.Cursor cursor;
		cursor = list.reader(list.head());
		if (Thread.currentThread().getName().equalsIgnoreCase("Thread-1")) {
			cursor.next();
		} else if (Thread.currentThread().getName()
				.equalsIgnoreCase("Thread-2")) {
			cursor.next();
			cursor.next();
		} else if (Thread.currentThread().getName()
				.equalsIgnoreCase("Thread-3")) {
			cursor.next();
			cursor.next();
			cursor.next();
		}
		System.out.println(Thread.currentThread().getName() + "-"
				+ cursor.current.value());
		cursor.writer().insertBefore(
				Thread.currentThread().getName() + "Before-0");
		cursor = list.reader(list.head());
		cursor.writer().insertAfter(
				Thread.currentThread().getName() + "After-0");

	}

	public void run5() throws Exception {
		CDLCoarseRW<String>.Cursor cursor;
		cursor = list.reader(list.head());
		if (Thread.currentThread().getName().equalsIgnoreCase("Thread-1")) {
			cursor.next();
		} else if (Thread.currentThread().getName()
				.equalsIgnoreCase("Thread-2")) {
			cursor.next();
			cursor.next();
		} else if (Thread.currentThread().getName()
				.equalsIgnoreCase("Thread-3")) {
			cursor.next();
			cursor.next();
			cursor.next();
		}
		System.out.println(Thread.currentThread().getName() + "-"
				+ cursor.current.value());

		// cursor = list.reader(list.head());
		cursor.writer().delete();

	}

	public void run6() throws Exception {
		CDLCoarseRW<String>.Cursor cursor;
		cursor = list.reader(list.head());

		int r = (int) (Math.random() * 100);
		for (int j = 0; j < r; j++) {
			cursor.next();
		}
		System.out.println("111" + Thread.currentThread().getName() + "-"
				+ cursor.current.value());
		cursor.writer().insertBefore(
				Thread.currentThread().getName() + "Before-0");
		cursor = list.reader(list.head());
		r = (int) (Math.random() * 100);
		for (int j = 0; j < r; j++) {
			cursor.next();
		}
		System.out.println("222" + Thread.currentThread().getName() + "-"
				+ cursor.current.value());

		cursor.writer().insertAfter(
				Thread.currentThread().getName() + "After-0");
		cursor = list.reader(list.head());
		r = (int) (Math.random() * 100);
		for (int j = 0; j < r; j++) {
			cursor.next();
		}
		System.out.println("333" + Thread.currentThread().getName() + "-"
				+ cursor.current.value());

		cursor.writer().delete();
	}

	public void run() {
		try {
			// run1();
			// run2();
			// run3();
			// run4();
			// run5();
			run6();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			System.out
					.println("Error--------------------------------------------------------------------------------");
			e.printStackTrace();
		}
	}
}

public class Starter extends Thread {
	public static void main(String[] args) {
		System.out.println("Starts....");
		CDLCoarseRW<String> list = new CDLCoarseRW<String>("Head Initial-0");
		MyRunnable myRunnable = new MyRunnable(list);
		try {
			myRunnable.insert();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < 4; i++) {
			new Thread(myRunnable).start();
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list.display();
	}
}