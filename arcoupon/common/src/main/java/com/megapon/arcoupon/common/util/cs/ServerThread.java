package com.megapon.arcoupon.common.util.cs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public abstract class ServerThread extends Thread {
	private static Log log = LogFactory.getLog(ServerThread.class);

	private List subThreadList = new ArrayList();

	public void setThreadName(String threadName){
		this.setName(threadName);
	}
	
	/*
		public ServerThread(String threadName) {
			this.setName(threadName);
		}
	 */
	public void run() {
		//log.debug("--------- " + Thread.currentThread().getName() + " start ---------");

		try {
			init();
			while (!Thread.currentThread().isInterrupted()) {
				try {
					loop();
				} catch (InterruptedException ex) {
					log.debug(Thread.currentThread().getName() + " :" + " is iterrupted");					
					break;
				} catch (Exception ex) {
					log.error(this, ex);
				}
			}
		} catch (Exception ex) {
			log.error(this, ex);
		} finally {
			try {
				exit();
				removeSubThreadAll();
			} catch (Exception ez) {
				log.error(this, ez);
			}
		}

		//log.debug("--------- " + Thread.currentThread().getName() + " end ---------");
	}

	public abstract void init() throws InterruptedException, Exception;

	public abstract void loop() throws InterruptedException, Exception;

	public abstract void exit() throws InterruptedException, Exception;

	public List getSubThreadList() {
		return subThreadList;
	}

	public void setSubThreadList(List subThreadList) {
		this.subThreadList = subThreadList;
	}

	public void addSubThread(Thread threadObject) {
		subThreadList.add(threadObject);
	}

	public void removeSubThread(Thread threadObject) {
		threadObject.interrupt();
		subThreadList.remove(threadObject);
	}

	public void removeSubThreadAll() {
		Iterator it = subThreadList.iterator();
		while (it.hasNext()) {
			Thread threadObject = (Thread) it.next();
			threadObject.interrupt();
		}

		subThreadList.removeAll(subThreadList);
	}
}
