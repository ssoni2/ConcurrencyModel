/*
 * ssoni2@buffalo.edu
 * 50096364
 */
public class CDLCoarseRW<T> extends CDLList<T> {
	public RWLock rwLock;

	public CDLCoarseRW(T v) {
		super(v);
		rwLock = new RWLock();
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
			try {
				rwLock.lockRead();
				super.previous();
			} finally {
				rwLock.unlockRead();
			}
		}

		// Move to the next element
		public void next() throws Exception {
			try {
				rwLock.lockRead();
				super.next();
			} finally {
				rwLock.unlockRead();
			}
		}
	}

	public class Writer extends CDLList<T>.Writer {
		public Writer(Element element) {

			super(element);
		}

		public boolean delete() throws Exception {
			Boolean ret = false;
			try {
				rwLock.lockWrite();
				ret = super.delete();
				return ret;
			} finally {
				rwLock.unlockWrite();
			}
		}

		public boolean insertBefore(T val) throws Exception {
			Boolean ret = false;
			try {
				rwLock.lockWrite();
				ret = super.insertBefore(val);
				return ret;
			} finally {
				rwLock.unlockWrite();
			}

		}

		public boolean insertAfter(T val) throws Exception {
			Boolean ret = false;
			try {
				rwLock.lockWrite();
				ret = super.insertBefore(val);
				return ret;
			} finally {
				rwLock.unlockWrite();
			}

		}
	}

}
