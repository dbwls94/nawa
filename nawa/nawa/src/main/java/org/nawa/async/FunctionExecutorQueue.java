package org.nawa.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.nawa.common.Functions;
import org.nawa.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunctionExecutorQueue {
	public static final int WORKER_SIZE = 5;
	private static final Logger logger = LoggerFactory.getLogger(FunctionExecutorQueue.class);
	private static FunctionExecutorQueue INSTANCE = null;
	
	private LinkedBlockingQueue<Functions> functionsQueue = new LinkedBlockingQueue<Functions>();
	private List<Worker> workers = new ArrayList<Worker>();
	
	private FunctionExecutorQueue() {
		for (int i = 0; i < WORKER_SIZE; i++) {
			Worker worker = new Worker();
			worker.start();
			workers.add(worker);
		} //for i
	} // INIT

	public static FunctionExecutorQueue getInstance() {
		synchronized (FunctionExecutorQueue.class) {
			if (INSTANCE == null)
				INSTANCE = new FunctionExecutorQueue();
			return INSTANCE;
		} // sync
	} // getInstance
	
	public synchronized void executeAsync(Functions function){
		functionsQueue.add(function);
	} //executeAsync
	
	class Worker extends Thread{
		@Override
		public void run() {
			for(;;){
				try{
					functionsQueue.take().execute();
				} catch(Exception e){
					logger.error("{}, errmsg : {}", e.getClass().getSimpleName(), e.getMessage());
					e.printStackTrace();
				} //catch
			} //for ;;
		} //run
	} //class
} // class