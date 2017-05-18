package org.jLOAF.Tetris;

import org.jLOAF.Perception;
import org.jLOAF.inputs.Input;
import org.jLOAF.inputs.complex.Matrix;
import org.jLOAF.sim.atomic.EuclideanDistance;
import org.jLOAF.sim.complex.Mean;

public class TetrisPerception implements Perception {

	public Input sense(double[][] board, double[][] piece) {
		TetrisInput ti = new TetrisInput(new Mean());
		Matrix brd = new Matrix("TetrisBoard", board,new Mean(),new EuclideanDistance());
		Matrix pce = new Matrix("TetrisPiece", piece,new Mean(),new EuclideanDistance());
		ti.add(brd);
		ti.add(pce);
		
		return ti;
	}

}
