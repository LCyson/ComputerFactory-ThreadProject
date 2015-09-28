package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import libraries.ImageLibrary;
import resource.Product;
import resource.Resource;

public class FactoryProductbin extends FactoryObject{
	

	private Lock mLock;

	int startAmount;
	private Queue<Product> mail;
	
	{
		mLabel = "Productbin";
		mImage = ImageLibrary.getImage(Constants.resourceFolder + "Box" + Constants.png);
		
		mLock = new ReentrantLock();
		startAmount = 0;
		mail = new LinkedList<Product>();
	}
	
	public FactoryProductbin(Rectangle inDimensions) {
		super(inDimensions);
	}
	
	@Override
	public void draw(Graphics g, Point mouseLocation) {
		super.draw(g, mouseLocation);
		g.setColor(Color.BLACK);
//		g.drawString(mResource.getQuantity()+"", centerTextX(g,mResource.getQuantity()+""), centerTextY(g));
		g.drawString(Integer.toString(startAmount), centerTextX(g,startAmount+""), centerTextY(g));
	}
	
	public void lock() {
		mLock.lock();
	}
	
	public void unlock() {
		mLock.unlock();
	}
	
	public void addAmount() {
		startAmount++;
	}
	
	public void reduceAmount() {
		startAmount--;
	}
	
	public int getFinishedProduct() {
		return startAmount;
	}

	public void insert(Product resource) {
		mail.add(resource);
	}
	
	public Product getStock() throws InterruptedException {
		while(mail.isEmpty()) {
			Thread.sleep(200);
		}
		return mail.remove();
	}

//	public void assemble(Product mProductToMake) throws InterruptedException {
//		for(int i = 0; i < mProductToMake.getResourcesNeeded().size(); ++i) {
//			Thread.sleep(500);
//		}
//	}
}
