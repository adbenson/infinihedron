package infinihedron.projections;

import java.util.List;

import infinihedron.pixelControl.models.Layer;
import infinihedron.pixelControl.models.Pixel;
import infinihedron.pixelControl.models.Segment;
import infinihedron.pixelControl.models.SegmentLine;

public class CylindricalProjection extends Projection {


	public Segment[] cylindricalSegments = {
		//          start edge,     end edge,       led channel, segment
		new Segment(Layer.A, 0, 	Layer.A, 2, 	0, 0),
		new Segment(Layer.A, 2, 	Layer.A, 4, 	1, 0),
		new Segment(Layer.A, 4, 	Layer.A, 6, 	2, 0),
		new Segment(Layer.A, 6, 	Layer.A, 8, 	3, 0),
		new Segment(Layer.A, 8, 	Layer.A, 10, 	4, 0),
	
		new Segment(Layer.A, 2, 	Layer.B, 2, 	0, 1),
		new Segment(Layer.A, 4, 	Layer.B, 4, 	1, 1),
		new Segment(Layer.A, 6, 	Layer.B, 6, 	2, 1),
		new Segment(Layer.A, 8, 	Layer.B, 8, 	3, 1),
		new Segment(Layer.A, 10, 	Layer.B, 10, 	4, 1),
	
		new Segment(Layer.C, 1, 	Layer.B, 0, 	0, 3),
		new Segment(Layer.B, 2, 	Layer.C, 1, 	0, 2),
		new Segment(Layer.C, 3, 	Layer.B, 2, 	1, 3),
		new Segment(Layer.B, 4, 	Layer.C, 3, 	1, 2),
		new Segment(Layer.C, 5, 	Layer.B, 4, 	2, 3),
		new Segment(Layer.B, 6, 	Layer.C, 5, 	2, 2),
		new Segment(Layer.C, 7, 	Layer.B, 6, 	3, 3),
		new Segment(Layer.B, 8, 	Layer.C, 7, 	3, 2),
		new Segment(Layer.C, 9, 	Layer.B, 8, 	4, 3),
		new Segment(Layer.B, 10, 	Layer.C, 9, 	4, 2),
	
		new Segment(Layer.C, 1, 	Layer.D, 1, 	5, 0),
		new Segment(Layer.C, 3, 	Layer.D, 3, 	5, 2),
		new Segment(Layer.C, 5, 	Layer.D, 5, 	5, 0),
		new Segment(Layer.C, 7, 	Layer.D, 7, 	5, 2),
		new Segment(Layer.C, 9, 	Layer.D, 9, 	5, 0),
	
		new Segment(Layer.D, 1, 	Layer.D, 3, 	5, 1),
		new Segment(Layer.D, 3, 	Layer.D, 5, 	5, 2),
		new Segment(Layer.D, 5, 	Layer.D, 7, 	6, 1),
		new Segment(Layer.D, 7, 	Layer.D, 9, 	6, 3),
	};
	
	Segment leftHalfSegment = new Segment(Layer.D, 0, 	Layer.D, 1, 	7, 1);
	Segment rightHalfSegment = new Segment(Layer.D, 9, 	Layer.D, 10, 	7, 1);
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getPixelValues'");
	} 
}

