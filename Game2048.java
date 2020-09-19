package game2048;

public class Game2048 {
	private boolean gameS=false;
	private int score;
	private final int xMax=4,yMax=4;
	private tile[][] board=new tile[xMax][yMax];
	private int tileX=100,tileY=100;
	

	public Game2048() {
		startGame();
		gameS=true;
	}

	public void startGame() {
		resetBoard();
		createTile(true);
		createTile(true);
	}
	public void resetBoard() {
		for(int i=0;i<xMax;i++) {
			for(int j=0;j<yMax;j++) board[i][j]=new tile(0,true);
		}
	}
	public int getScore() {
		return score;
	}
	public int getTileX() {
		return tileX;
	}
	public int getTileY() {
		return tileY;
	}
	public void setTile() {
		tileX=tileY=100;
	}
	public boolean createTile(boolean start) {
		boolean tileNotCreated=true;
		int x=0;
		int y=0;
		int ranTile=2;

		while(tileNotCreated) {
			x=(int) (Math.random()*4);
			y=(int) (Math.random()*4);
			
			if(board[x][y].getEmpty()) {
				tileNotCreated=false;
				if((int)(Math.random()*10+1)==1)ranTile=4;
				board[x][y]=new tile(ranTile,false);
				if(!start) {
					tileX=x;
					tileY=y;
				}
				return true;
			}
		}
		return false;
	}
	public boolean getGame() {
		return gameS;
	}

	public int getTileValue(int x,int y) {
		return board[x][y].getValue();
	}
	public boolean moveTiles(int direction) {
		int n=0,m=0;
		int i=0,jTemp=0;
		int incI=1,incJ=1;
		boolean moved=false;
		if(direction==0) {
			m=-1;
			n=0;

		}
		else if(direction==1) {
			m=1;
			n=0;
			jTemp=yMax-1;
			incJ=-1;
		}
		else if(direction==2) {
			m=0;
			n=-1;
		}
		else if(direction==3) {
			m=0;
			n=1;
			i=xMax-1;
			incI=-1;
		}
		for(;i<xMax&&i>=0;i+=incI) {
			for(int j=jTemp;j<yMax&&j>=0;j+=incJ) {
				int x=i;
				int y=j;
				if((!board[x][y].getEmpty())&&x+n>=0&&x+n<4&&y+m>=0&&y+m<4) {
					boolean start=true;
					try{
						while(start) {
							if(board[x+n][y+m].getEmpty()) {
								board[x+n][y+m]=new tile(board[x][y].getValue(),false);
								board[x][y]=new tile(0,true);
								x+=n;
								y+=m;
								moved=true;
							}
							else if((!(board[x][y].getMerged()||board[x+n][y+m].getMerged()))&&board[x+n][y+m].getValue()==board[x][y].getValue()){
								board[x+n][y+m]=new tile(board[x+n][y+m].getValue()*2,false);
								score+=board[x+n][y+m].getValue();
								board[x+n][y+m].setMerged(true);
								board[x][y]=new tile(0,true);
								x+=n;
								y+=m;
								moved=true;
							}
							else {
								start=false;
							}
							
							if(!(x+n>=0&&x+n<4&&y+m>=0&&y+m<4)) {
								break;
							}

						}
					}
					catch(java.lang.ArrayIndexOutOfBoundsException e) {
						
					}
				}
			}
		}
		resetMerged();
		return moved;
	}

	public boolean check2048() {
		for(int i=0;i<xMax;i++) {
			for(int j=0;j<yMax;j++) {
				if(board[i][j].getValue()==2048)return true;
			}
		}
		return false;
	}

	private void resetMerged() {
		for(int i=0;i<xMax;i++) {
			for(int j=0;j<yMax;j++) {
				board[i][j].setMerged(false);

			}
		}

	}

	public boolean checkEmpty() {
		for(int i=0;i<xMax;i++) {
			for(int j=0;j<yMax;j++) {
				if(board[i][j].getEmpty())return true;
			}
		}
		return false;
	}

	public boolean checkLoss() {
		for(int i=0;i<xMax;i++) {
			for(int j=0;j<yMax;j++) {
				if(board[i][j].getEmpty())return false;
				if(i!=0)if(board[i-1][j].getValue()==board[i][j].getValue())return false;
				if(i!=xMax-1)if(board[i+1][j].getValue()==board[i][j].getValue())return false;
				if(j!=0)if(board[i][j-1].getValue()==board[i][j].getValue())return false;
				if(j!=yMax-1)if(board[i][j+1].getValue()==board[i][j].getValue())return false;

			}
		}	
		return true;
	}


	class tile {
		int value;
		boolean merged=false;
		boolean empty;

		public tile(int value, boolean emp) {
			this.value=value;
			this.empty=emp;
		}

		public int getValue() {
			return this.value;
		}
		public boolean getMerged() {
			return merged;
		}

		public void setValue(int v) {
			value=v;
		}
		public void setMerged(boolean s) {
			merged=s;
		}

		public boolean getEmpty() {
			return empty;
		}
	}
}
