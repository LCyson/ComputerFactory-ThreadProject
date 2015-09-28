package client;

import libraries.ImageLibrary;
import resource.Product;


public class FactoryShipper extends FactoryWorker {

	Product mProductToShip;
	
	{
		mImage = ImageLibrary.getImage(Constants.resourceFolder + "stockPerson_empty" + Constants.png);
	}
	
	FactoryShipper(int inNumber, FactoryNode startNode, FactorySimulation inFactorySimulation) {
		super(inNumber, startNode, inFactorySimulation);
		mLabel = "Shipper "+inNumber;
	}

	@Override
	public void run() {
		mLock.lock();
		try {
			while(true) {
				if(mProductToShip == null) {
					mDestinationNode = mFactorySimulation.getNode("Productbin");
					mShortestPath = mCurrentNode.findShortestPath(mDestinationNode);
					mNextNode = mShortestPath.pop();
					atLocation.await();
					while(!mDestinationNode.aquireNode())Thread.sleep(1);
					mProductToShip = mFactorySimulation.getProductBin().getStock();
					mFactorySimulation.getProductBin().reduceAmount();
					mImage = ImageLibrary.getImage(Constants.resourceFolder + "stockPerson_box" + Constants.png);
					Thread.sleep(1000);
					mDestinationNode.releaseNode();
				} else {
					mDestinationNode = mFactorySimulation.getNode("MailBox");
					mShortestPath = mCurrentNode.findShortestPath(mDestinationNode);
					mNextNode = mShortestPath.pop();
					atLocation.await();
					
					mImage = ImageLibrary.getImage(Constants.resourceFolder + "stockPerson_empty" + Constants.png);
					mProductToShip.shipProduct();
					mFactorySimulation.getTaskBoard().shipTask(mProductToShip);
					mProductToShip = null;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mLock.unlock();
	}
	
}
