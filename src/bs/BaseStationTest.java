package bs;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import simulators.robot.TestTool;
import bs.ui.BaseStationGui;

/**
 * Entry-point to create and initialize the Base Station.
 */
public class BaseStationTest {

	/**
	 * Main entry point into the Base Station program
	 */
	public static void main(String[] args) {
		try {
			PipedOutputStream BStoOBS = new PipedOutputStream();
			PipedInputStream OBSfromBS = new PipedInputStream(BStoOBS);
			PipedOutputStream OBStoBS = new PipedOutputStream();
			PipedInputStream BSfromOBS = new PipedInputStream(OBStoBS);

			SimulatorConnection baseStation = new SimulatorConnection(BSfromOBS, BStoOBS);

			SimulatorConnection onBoardSystem = new SimulatorConnection(OBSfromBS,
					OBStoBS);

			TestTool testTool = new TestTool(onBoardSystem);
			testTool.init(true);

			RobotController robotController = new RobotController(baseStation);
			BaseStationGui gui = new BaseStationGui(robotController);
			gui.generateBaseStationGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
