package org.jLOAF.Tetris;

import org.jLOAF.inputs.ComplexInput;
import org.jLOAF.inputs.Input;
import org.jLOAF.sim.SimilarityMetricStrategy;

public class TetrisInput extends ComplexInput {
	
	private static final long serialVersionUID = 1L;
	private static SimilarityMetricStrategy simMet;

	public TetrisInput(SimilarityMetricStrategy sim){
		super("Tetris Input",sim);
	}

	

}
