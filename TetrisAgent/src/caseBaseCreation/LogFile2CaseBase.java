package caseBaseCreation;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.jLOAF.Tetris.TetrisAction;
import org.jLOAF.Tetris.TetrisInput;
import org.jLOAF.action.AtomicAction;
import org.jLOAF.action.ComplexAction;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.inputs.AtomicInput;
import org.jLOAF.inputs.ComplexInput;
import org.jLOAF.inputs.Feature;
import org.jLOAF.inputs.complex.Matrix;
import org.jLOAF.sim.SimilarityMetricStrategy;




public class LogFile2CaseBase
{

    
    private CaseBase m_casebase;
    private SimilarityMetricStrategy matrixCellsim;
    private SimilarityMetricStrategy inputsim;
    
    
   
    
    /*
	 * outputs the casebase passed to it in a .cb file with the name also passed to it
	 * @param cb the casebase to be saved
	 * @param outputFile the file in which the casebase will be saved
	 */
	private void saveCaseBase(CaseBase cb, String outputFile) {
		
		CaseBase.save(cb, outputFile);
	}
    
    public LogFile2CaseBase() {
	//initialize the CaseBase
	this.m_casebase= new CaseBase();
    }


    public void parseLogFile(String logFile, String caseBaseFile) throws IOException {
    	//check params
    	if(logFile == null || caseBaseFile == null){
    	    throw new IllegalArgumentException("Null parameters given to constructor.");
    	}
    	
	//open the log file
	BufferedReader in = new BufferedReader(new FileReader(logFile));
	
	String line = null;
	CaseBase cb = new CaseBase();
	
	System.out.println("Reading log data...");
	//read in each line of the log file
	while ((line = in.readLine()) != null){
		if(line.equals("") || line.equals("\n")){
			continue;
		}
		//line.replaceAll("?", " ");
	    Scanner s = new Scanner(line);
	    s.useDelimiter("\\?");
	    String board = s.next();
	    String piece = s.next();
	    int x = new Integer(s.next());
	    int y = new Integer(s.next());
	    int rot = new Integer(s.next());
	    double score = new Double(s.next());
	    
	    
	    ComplexAction tetrisact = new TetrisAction();
	    AtomicAction acts = new AtomicAction("TetrisFeatures");
	    Feature f_x = new Feature(x);
	    Feature f_y = new Feature(y);
	    Feature f_rot = new Feature(rot);
	    Feature f_score = new Feature(score);
	    acts.addFeature(f_x);
	    acts.addFeature(f_y);
	    acts.addFeature(f_rot);
	    acts.addFeature(f_score);
	    
	    tetrisact.add(acts);
	    
	    
	    TetrisInput ti = new TetrisInput(inputsim);
	    
	    
	    double[][] boardMat = new double[20][10];
		
		Scanner scn = new Scanner(board);
		scn.useDelimiter(",");
		int cnt = 0;
		while(scn.hasNext()){
			String currRow = scn.next();
			if(cnt > 3){
				Scanner s2 = new Scanner(currRow);
				s2.useDelimiter("-");
				for(int ii=0;ii<10;ii++){
					boardMat[cnt-4][ii] =  new Double(s2.next());
				}
			}
			cnt++;
		}
	    
		Matrix tb = new Matrix("TetrisBoard",boardMat,inputsim,matrixCellsim);
		
		
		double[][] pieceMat = new double[4][4];
		for(int ii=0; ii< pieceMat.length; ii++){
			for(int jj=0; jj<pieceMat[0].length; jj++){
				pieceMat[ii][jj] = 0.0;
			}
		}
		
		scn = new Scanner(piece);
		scn.useDelimiter("-");
		
		//all tetris pieces have 4 blocks
		String v1 = scn.next();
		pieceMat[new Integer(v1.substring(0, 1))][new Integer(v1.substring(1, 2))] = 1;
		String v2 = scn.next();
		pieceMat[new Integer(v2.substring(0, 1))][new Integer(v2.substring(1, 2))] = 1;
		String v3 = scn.next();
		pieceMat[new Integer(v3.substring(0, 1))][new Integer(v3.substring(1, 2))] = 1;
		String v4 = scn.next();
		pieceMat[new Integer(v4.substring(0, 1))][new Integer(v4.substring(1, 2))] =  1;

		
	    Matrix tp = new Matrix("TetrisPiece", pieceMat,inputsim,matrixCellsim);
    
	    ti.add(tb);
	    ti.add(tp);
	    
	    Case c = new Case(ti,tetrisact);
	    
	    cb.add(c);	    
	}
	
	System.out.println("Finished reading log file.");
	System.out.println(cb.getSize() + " Cases were extracted.");
	
	//close the file input stream
	in.close();
	saveCaseBase(cb,caseBaseFile);
    }
    


    

}
