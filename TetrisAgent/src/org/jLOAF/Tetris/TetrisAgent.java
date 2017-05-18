package org.jLOAF.Tetris;

import org.jLOAF.Agent;
import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.inputs.Input;
import org.jLOAF.inputs.atomic.MatrixCell;
import org.jLOAF.inputs.complex.Matrix;
import org.jLOAF.reasoning.SimpleKNN;
import org.jLOAF.reasoning.WeightedKNN;
import org.jLOAF.sim.atomic.Equality;
import org.jLOAF.sim.complex.Mean;


public class TetrisAgent extends Agent {

	public TetrisAgent(){
		super(null,null,null,null);
		//sets the similarity metrics that are used
		
		
		//create the Tetris-specific motor control and perception
		this.mc = new TetrisMotorControl();
		this.p = new TetrisPerception();
		
		//use a general reasoning module (1-NN)
		
		
		
	}

	public String go(double[][] board, double[][] piece) {
		Input in = ((TetrisPerception)this.p).sense(board,piece);
		Action act = this.r.selectAction(in);
		return this.mc.control(act);
		
	}

	@Override
	public TetrisAction run(Input input) {
		// TODO Auto-generated method stub
		return (TetrisAction)this.r.selectAction(input);
	}

	@Override
	public void train(CaseBase casebase) {
		this.cb=casebase;
		this.r = new WeightedKNN(5, cb);
		
	}
}
