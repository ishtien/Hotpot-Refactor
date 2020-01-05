package hotpot;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import ui.ResultUI;

class ExitAdapter extends WindowAdapter {
	static ResultUI Rframe;
	private JFrame frame;
	private GameResult result;
	
	public ExitAdapter(JFrame frame, GameResult result, ResultUI Rframe) {
		this.frame = frame;
		this.result = result;
		this.Rframe = Rframe;
	}
	
    public void windowClosing(WindowEvent e) {
    	Rframe = new ResultUI(result.getSumCal(),result.getSumPrice(),result.getNickname(),result.getFoodMost(0),result.getFoodMost(1),result.getFoodMost(2));
    	Rframe.setLocation(50, 50);
        Rframe.setVisible(true);
        frame.setVisible(false);
   }
}
