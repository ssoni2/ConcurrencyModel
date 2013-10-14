/*
 * ssoni2@buffalo.edu
 * 50096364
 */

public class CDLListFineRW<T> extends CDLList<T> {
	public CDLListFineRW(T v) {
		super(v);
	}

	public Cursor reader(Element from) {
		return new Cursor(from);
	}

	public class Cursor extends CDLList<T>.Cursor {
		public Cursor(Element from) {
			super(from);
		}

		public Writer writer() {
			return new Writer(super.current);
		}

		public void previous() throws Exception {
			Element temp = null;
			try {
				temp = this.current;
				temp.lock = 1;
				temp.rwLock.lockRead();
				super.previous();
			} finally {
				temp.rwLock.unlockRead();
				temp.lock = 0;
			}

		}

		// Move to the next element
		public void next() throws Exception {
			Element temp = null;
			try {
				temp = this.current;
				temp.lock = 1;
				temp.rwLock.lockRead();
				super.next();
			} finally {
				temp.rwLock.unlockRead();
				temp.lock = 0;
			}

		}
	}

	public class Writer extends CDLList<T>.Writer {
		public Writer(Element element) {

			super(element);
		}

		public boolean delete() throws Exception {

			Boolean ret = false;
			// Initializing a boolean variable
			System.out.println("!" + Thread.currentThread().getName()
					+ "Started delete " + this.writer.previous.value()
					+ this.writer.value() + this.writer.next.value());
			Element tempPrevious = null;
			Element tempNext = null;
			while (true) {
				if (this.writer.next == null || this.writer.previous == null) {
					throw new Exception(
							"Not deleted. Invalid Element. Doesnot exist in the list");
				}
				if (this.writer.previous == this.writer) {
					throw new Exception(
							"Not deleted. Last element. Cannot be deleated");
				}
				tempPrevious = null;
				tempNext = null;
				if (this.writer.lock == 1 || this.writer.next.lock == 1) {
					Thread.sleep(10);
					System.out.println("1 Waiting-"
							+ Thread.currentThread().getName()
							+ this.writer.value());
					continue;
				}
				try {

					tempPrevious = this.writer.previous;
					tempPrevious.lock = 1;
					tempPrevious.rwLock.lockWrite();

					System.out.println("Locked"
							+ Thread.currentThread().getName() + "- previous");

					if (this.writer.previous.previous == this.writer) {
						if (this.writer.lock == 1) {
							tempPrevious.rwLock.unlockWrite();
							tempPrevious.lock = 0;

							System.out.println("Canceled Locked"
									+ Thread.currentThread().getName()
									+ "- previous");
							continue;
						}
						ret = super.delete();
						tempPrevious.rwLock.unlockWrite();
						tempPrevious.lock = 0;

						System.out.println("Canceled Locked"
								+ Thread.currentThread().getName()
								+ "- previous");
						return ret;
					}

					if (this.writer.lock == 1 || this.writer.next.lock == 1) {
						tempPrevious.rwLock.unlockWrite();
						tempPrevious.lock = 0;
						System.out.println("Canceled Locked"
								+ Thread.currentThread().getName()
								+ "- previous");
						continue;
					}

					try {
						this.writer.lock = 1;
						this.writer.rwLock.lockWrite();
						System.out.println("Locked"
								+ Thread.currentThread().getName()
								+ "- current");
						if (tempPrevious != this.writer.previous) {
							System.out.println("Invalid state here-"
									+ Thread.currentThread().getName()
									+ tempPrevious.value()
									+ this.writer.value()
									+ this.writer.previous.value());
							this.writer.rwLock.unlockWrite();
							this.writer.lock = 0;
							tempPrevious.rwLock.unlockWrite();
							tempPrevious.lock = 0;
							continue;
						}
						// if(this.writer.previous.next!=this.writer){
						if (this.writer.next.lock == 1) {
							this.writer.rwLock.unlockWrite();
							this.writer.lock = 0;
							System.out.println("Canceled Locked"
									+ Thread.currentThread().getName()
									+ "- current");
							tempPrevious.rwLock.unlockWrite();
							tempPrevious.lock = 0;
							System.out.println("Canceled Locked"
									+ Thread.currentThread().getName()
									+ "- previous");
							continue;
						}
						// mySleep();
						try {
							tempNext = this.writer.next;

							tempNext.lock = 1;
							tempNext.rwLock.lockWrite();
							System.out.println("Locked"
									+ Thread.currentThread().getName()
									+ "- next");
							// set the lock for next element
							// mySleep();

							ret = super.delete();
							tempNext.rwLock.unlockWrite();
							tempNext.lock = 0;
							// reset next lock
							System.out.println("Un Locked"
									+ Thread.currentThread().getName()
									+ "- next");
							this.writer.rwLock.unlockWrite();
							this.writer.lock = 0;
							// reset the lock for current element
							System.out.println("Un Locked"
									+ Thread.currentThread().getName()
									+ "- current");
							tempPrevious.rwLock.unlockWrite();
							tempPrevious.lock = 0;
							System.out.println("Un Locked"
									+ Thread.currentThread().getName()
									+ "- previous");
							return ret;
						} catch (Exception e) {
							tempNext.rwLock.unlockWrite();
							tempNext.lock = 0;
							throw e;
						}
					} catch (Exception e) {
						this.writer.rwLock.unlockWrite();
						this.writer.lock = 0;
						throw e;
					}
				} catch (Exception e) {
					tempPrevious.rwLock.unlockWrite();
					tempPrevious.lock = 0;
					throw e;
				}
			}
		}

		// method to insert element before current element in a fine grained
		// synchronized way
		public boolean insertBefore(T val) throws Exception {
			Boolean ret = false;
			// Initializing a boolean variable
			// System.out.println(Thread.currentThread().getName()+"Started insert "+this.writer.previous.value()+this.writer.value()+this.writer.next.value());
			Element tempPrevious = null;
			while (true) {
				if (this.writer == null || this.writer.previous == null) {
					throw new Exception(
							"Invalid Element. Doesnot exist in the list");
				}
				// infinite look to keep looping until acquiring previous and
				// current element lock
				tempPrevious = null;
				if (this.writer.lock == 1) {
					// while(this.writer.lock==1);
					Thread.sleep(10);
					// if current element is already locked then continue and
					// try again
					// System.out.println("Waiting-"+Thread.currentThread().getName()+this.writer.value());
					continue;
				}
				try {
					// if current element is not locked then lock previous
					tempPrevious = this.writer.previous;
					tempPrevious.lock = 1;
					tempPrevious.rwLock.lockWrite();
					// pointer to previous so that it is not changed

					// set the previus element lock status
					System.out.println("Locked"
							+ Thread.currentThread().getName() + "- previous");
					if (tempPrevious == this.writer) {
						// if list have only one element
						ret = super.insertBefore(val);
						tempPrevious.rwLock.unlockWrite();
						tempPrevious.lock = 0;

						// reset the previous element lock
						System.out.println("Released Locked"
								+ Thread.currentThread().getName()
								+ "- previous");
						return ret;
					}
					// if(Compar)

					if (this.writer.lock == 1) {
						// if current element is already locked release lock for
						// previous & then continue and
						// try again
						tempPrevious.rwLock.unlockWrite();
						tempPrevious.lock = 0;

						System.out.println("Canceled Locked"
								+ Thread.currentThread().getName()
								+ "- previous");
						continue;
					}
					// mySleep();
					try {
						this.writer.lock = 1;
						this.writer.rwLock.lockWrite();
						if (tempPrevious != this.writer.previous) {
							/*
							 * System.out.println("Invalid state here-" +
							 * Thread.currentThread().getName() +
							 * tempPrevious.value() + this.writer.value() +
							 * this.writer.previous.value());
							 */
							this.writer.rwLock.unlockWrite();
							this.writer.lock = 0;
							tempPrevious.rwLock.unlockWrite();
							tempPrevious.lock = 0;
							continue;
						}

						// set the lock for current element
						System.out.println("Locked"
								+ Thread.currentThread().getName()
								+ "- current");
						// mySleep();

						ret = super.insertBefore(val);
						this.writer.rwLock.unlockWrite();
						this.writer.lock = 0;
						// reset the lock for current element
						System.out.println("Un Locked"
								+ Thread.currentThread().getName()
								+ "- current");
						tempPrevious.rwLock.unlockWrite();
						tempPrevious.lock = 0;
						// reset the lock for previous element
						System.out.println("Un Locked"
								+ Thread.currentThread().getName()
								+ "- previous");
						return ret;
					} catch (Exception e) {
						this.writer.rwLock.unlockRead();
						this.writer.lock = 0;
						throw e;
					}
				} catch (Exception e) {
					tempPrevious.rwLock.unlockRead();
					tempPrevious.lock = 0;
					throw e;
				}

			}

		}

		// method to insert element after current element in a fine grained
		// synchronized way
		public boolean insertAfter(T val) throws Exception {
			Boolean ret = false;
			// Initializing a boolean variable
			System.out.println(Thread.currentThread().getName()
					+ " Started insert " + this.writer.previous.value()
					+ this.writer.value() + this.writer.next.value());
			Element tempNext = null;
			while (true) {

				// infinite look to keep looping until acquiring current and
				// next element lock
				if (this.writer == null || this.writer.next == null) {
					throw new Exception(
							"Invalid Element. Doesnot exist in the list");
				}
				tempNext = null;
				if (this.writer.next.lock == 1) {

					Thread.sleep(10);
					// if next element is already locked then continue and
					// try again as any way we need this later
					System.out.println("Waiting-"
							+ Thread.currentThread().getName()
							+ this.writer.value());
					continue;
				}

				try {// System.out.println("omg");

					this.writer.lock = 1;
					this.writer.rwLock.lockWrite();
					// System.out.println("Locked"
					// + Thread.currentThread().getName() + "- current");
					if (this.writer == this.writer.next) {
						// if list have only one element
						ret = super.insertAfter(val);
						this.writer.rwLock.unlockWrite();
						this.writer.lock = 0;

						// reset the current element lock
						// System.out.println("Released Locked"
						// + Thread.currentThread().getName()
						// + "- current");
						return ret;
					}
					// if(Compar)

					if (this.writer.next.lock == 1) {
						// if next element is already locked release current
						// lock for others
						// then continue and try again
						this.writer.rwLock.unlockWrite();
						this.writer.lock = 0;
						/*
						 * System.out.println("Canceled Locked" +
						 * Thread.currentThread().getName() + "- current");
						 */
						continue;
					}
					// mySleep();
					try {

						tempNext = this.writer.next;

						tempNext.lock = 1;
						tempNext.rwLock.lockWrite();
						/*
						 * System.out.println("Locked" +
						 * Thread.currentThread().getName() + "- next");
						 */
						// set the lock for next element
						// mySleep();

						ret = super.insertAfter(val);
						tempNext.rwLock.unlockWrite();
						tempNext.lock = 0;
						// reset next lock
						/*
						 * System.out.println("Un Locked" +
						 * Thread.currentThread().getName() + "- next");
						 */
						this.writer.rwLock.unlockWrite();
						this.writer.lock = 0;
						// reset the lock for current element
						/*
						 * System.out.println("Un Locked" +
						 * Thread.currentThread().getName() + "- current");
						 */

						return ret;
					} catch (Exception e) {
						tempNext.rwLock.unlockWrite();
						tempNext.lock = 0;
						throw e;
					}
				} catch (Exception e) {
					this.writer.rwLock.unlockWrite();
					this.writer.lock = 0;
					throw e;
				}

			}

		}
	}

	public void mySleep2() {
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
