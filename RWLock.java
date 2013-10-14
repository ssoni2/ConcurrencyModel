/*
 * ssoni2@buffalo.edu
 * 50096364
 */
public class RWLock {
	private final java.util.concurrent.atomic.AtomicInteger lock;

	// private volatile boolean check = false;

	public RWLock() {
		lock = new java.util.concurrent.atomic.AtomicInteger(1);

	}

	public void lockRead() {
		// System.out.println(Thread.currentThread().getName()
		// + "-lockAcquiredRead start");
		// System.out.println("called lock read");
		while (true) {

			int temp = lock.get();
			if (temp > 0) {
				if (lock.compareAndSet(temp, ++temp))
					break;
				else
					continue;
			} else {
				try {
					synchronized (this) {
						if (temp == lock.get()) {
							wait();
						}
					}
				} catch (InterruptedException e) {
					// If wait gets interrupted we still continue to acquire
					// wait
					e.printStackTrace();
				}
			}

		}

		// System.out.println(Thread.currentThread().getName()
		// + "-lockAcquiredRead end");
	}

	public void unlockRead() {
		// System.out.println(Thread.currentThread().getName()
		// + "-lockReleasedRead start");
		// System.out.println("called unlock read");
		while (true) {

			int temp = lock.get();
			if (temp > 1) {
				if (lock.compareAndSet(temp, --temp)) {
					if (lock.compareAndSet(-1, 1))
						;
					synchronized (this) {
						notifyAll();
					}
					break;
				}

			} else if (temp < -1) {
				if (lock.compareAndSet(temp, ++temp)) {
					if (lock.compareAndSet(-1, 1))
						;
					synchronized (this) {
						notifyAll();
					}
					break;
				}
			}

		}

		// System.out.println(Thread.currentThread().getName()
		// + "-lockReleasedRead end");
	}

	public void lockWrite() {
		System.out.println(Thread.currentThread().getName()
				+ "-lockAcquiredWrite start");
		// System.out.println("called lock write");

		// boolean check = false;
		while (true) {
			if (lock.compareAndSet(1, 0))
				break;
			else {

				int temp = lock.get();
				if (temp > 1) {
					if (lock.compareAndSet(temp, -temp)) {
						try {
							synchronized (this) {
								if (temp == lock.get()) {
									wait();
								}
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else if (temp < 1) {
					try {
						synchronized (this) {
							if (temp == lock.get()) {
								wait();
							}
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		// System.out.println(Thread.currentThread().getName()
		// + "-lockAcquiredWrite end");
	}

	public void unlockWrite() {
		// System.out.println("called unlock wrie");
		// System.out.println(Thread.currentThread().getName()
		// + "-lockReleasedWrite start");
		while (true) {

			if (lock.compareAndSet(0, 1)) {
				synchronized (this) {
					notifyAll();
					break;
				}
			}

		}
		// System.out.println(Thread.currentThread().getName()
		// + "-lockReleasedWrite end");
	}
}
