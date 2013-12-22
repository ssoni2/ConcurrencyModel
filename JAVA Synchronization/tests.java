/*
 * ssoni2@buffalo.edu
 * 50096364
 */
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;



public class tests {
	@Test
	public void CDLCoarseRW() {
		try {
			CDLCoarseRW<String> list = new CDLCoarseRW<String>("hi");

			CDLCoarseRW<String>.Element head = list.head();
			CDLCoarseRW<String>.Cursor c = list.reader(list.head());

			for (int i = 74; i >= 65; i--) {
				char val = (char) i;
				c.writer().insertBefore("" + val);
			}

			List<Thread> threadList = new ArrayList<Thread>();
			for (int i = 0; i < 1000; i++) {
				NormalThread nt = new NormalThread(list, i);
				threadList.add(nt);
			}

			RandomThread rt = new RandomThread(list);
			threadList.add(rt);

			try {
				for (Thread t : threadList) {
					t.start();
				}
				for (Thread t : threadList) {
					t.join();
				}
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}

			System.out.print("\n\nCDLCoarseRW:");
			list.display();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	@Test
	public void CDLListFineRW() {
		try {
			CDLListFineRW<String> list = new CDLListFineRW<String>("hi");
			CDLListFineRW<String>.Element head = list.head();
			CDLListFineRW<String>.Cursor c = list.reader(list.head());

			for (int i = 74; i >= 65; i--) {
				char val = (char) i;
				c.writer().insertBefore("" + val);
			}

			List<Thread> threadList = new ArrayList<Thread>();
			for (int i = 0; i < 1000; i++) {
				NormalThread nt = new NormalThread(list, i);
				threadList.add(nt);
			}

			RandomThread rt = new RandomThread(list);
			threadList.add(rt);

			try {
				for (Thread t : threadList) {
					t.start();
				}
				for (Thread t : threadList) {
					t.join();
				}
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}

			System.out.print("\n\nCDLListFineRW:");
			list.display();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}
