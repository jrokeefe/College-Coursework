
public class test {
	public static void main(String[] args) {
		char[][] testInfo = new char[3][3];
		testInfo[0][0] = 'L';
		testInfo[0][1] = '.';
		testInfo[0][2] = '|';
		testInfo[1][0] = 'L';
		testInfo[1][1] = '|';
		testInfo[1][2] = '|';
		testInfo[2][0] = 'L';
		testInfo[2][1] = '_';
		testInfo[2][2] = '_';
		Maze test = new Maze(testInfo);
		//test.setStart(0, 0);
		//test.setFinish(2, 0);
		test.displayMaze();
		//test.solveMaze();
		//test.displayMaze();
		//while (test.path.getSize() != 0) {
		//	System.out.println(test.path.peek().row + "," + test.path.peek().col);
		//	test.path.pop();
		//}
		//System.out.println(test.east);
	}
}
