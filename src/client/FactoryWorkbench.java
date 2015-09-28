package client;

import java.awt.Rectangle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import resource.Product;
import libraries.ImageLibrary;

public class FactoryWorkbench extends FactoryObject{

	private Lock mLock;
	
	{
		mLabel = "Workbench";
		mImage = ImageLibrary.getImage(Constants.resourceFolder + "workbench" + Constants.png);
		
		mLock = new ReentrantLock();
	}
	
	public FactoryWorkbench(Rectangle inDimensions) {
		super(inDimensions);
	}
	
	public void lock() {
		mLock.lock();
	}
	
	public void unlock() {
		mLock.unlock();
	}

	public void assemble(Product mProductToMake) throws InterruptedException {
		for(int i = 0; i < mProductToMake.getResourcesNeeded().size(); ++i) {
			Thread.sleep(500);
		}
	}

}
