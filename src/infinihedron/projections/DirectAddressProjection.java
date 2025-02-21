package infinihedron.projections;

import java.util.List;

import infinihedron.pixelControl.models.Pixel;
import infinihedron.pixelControl.models.SegmentLine;

public class DirectAddressProjection extends Projection {

	@Override
	public List<Pixel> getPixels() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getPixels'");
	}

	@Override
	public List<SegmentLine> getSegments() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getSegments'");
	}

	@Override
	public int[] getPixelValues(int[] displayedPixels, int offset) {
		return pixelValues;
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("Unimplemented method 'getPixelValues'");
	}

}
