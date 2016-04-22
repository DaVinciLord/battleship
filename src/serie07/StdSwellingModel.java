package serie07;

import java.awt.Dimension;
import java.util.Observable;

import util.Contract;

public class StdSwellingModel extends Observable implements SwellingModel {
	private Dimension curdim;
	private Dimension mindim;
	
	public StdSwellingModel() {
		curdim = DEFAULT_MINIMAL_DIM;
		mindim = DEFAULT_MINIMAL_DIM;
	}

	@Override
	public Dimension getMinimalDimension() {
		return new Dimension(mindim);
	}

	@Override
	public Dimension getDimension() {
		return new Dimension(curdim);
	}

	@Override
	public void setMinimalDimension(Dimension dim) {
	        Contract.checkCondition(dim != null, "null dim");
	        Contract.checkCondition(0 <= dim.width 
	        		&& dim.width <= MAXIMAL_DIM.width, "invalid dim");
	        Contract.checkCondition(0 <= dim.height 
	        		&& dim.height <= MAXIMAL_DIM.height, "invalid dim");
	        mindim.setSize(dim.width, dim.height);
	        curdim.setSize(Math.max(getDimension().width, dim.width),
	        		Math.max(getDimension().height, dim.height));
	}

	@Override
	public void scale(double factor) {
		Contract.checkCondition(factor >= -1, "invalid factor");
		curdim.setSize(Math.min(
			              Math.max(
			    	           getDimension().width * (1 + factor),
			    	           getMinimalDimension().width),
			    	            MAXIMAL_DIM.width),
						Math.min(
					      Math.max(
					            getDimension().height * (1 + factor),
					            getMinimalDimension().height),
					            MAXIMAL_DIM.height));
	}

}
