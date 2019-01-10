import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Image;

public class HAngryBirds extends JFrame{

	public HAngryBirds(){
		setSize(800,500);
		setTitle("HAngry Birds...");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		MyJPanel myJPanel= new MyJPanel();
		Container c = getContentPane();
		c.add(myJPanel);
		setVisible(true);
		setResizable(false);
	}

	public static void main(String[] args){
		new HAngryBirds();
	}

	public class MyJPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener{
		Timer timer,timer2;
		Image image,image2;
		int my_x, my_y;
		int mouse_x, mouse_y;
		int start_x, start_y;
		int init_x=100, init_y=375;
		double t=0.0, v=100.0;
		double v_x, v_y;
		int my_width, my_height;
		int grab_flag=0;

		int n;
		int enemy_x[];
		int enemy_y[];
		int enemy_move[];
		int enemy_alive[];
		int enemy_width;
		int enemy_height;
		int num_of_alive;




		public MyJPanel(){
			setBackground(Color.white);
			addMouseListener(this);
			addMouseMotionListener(this);

			ImageIcon icon = new ImageIcon("bird.jpg");
			image = icon.getImage();

			ImageIcon icon2 = new ImageIcon("enemy2.png");
			image2 = icon2.getImage();

			my_width = image.getWidth(this);
			my_height = image.getHeight(this);

			enemy_width = image2.getWidth(this);
			enemy_height = image2.getHeight(this);
			my_x = init_x;
			my_y = init_y;
			int i;
			n = 10;
			num_of_alive = n;
			enemy_x = new int[n];
			enemy_y = new int[n];

			enemy_move = new int[n];
			enemy_alive = new int[n];

			timer2 = new Timer(50,myTimerListener2);
			timer2.start();


			for(i=0;i<n/2;i++){
				enemy_y[i] = 70*(i+1)-50;
				enemy_x[i] = 600;
			}
			for(i=n/2;i<n;i++){
				enemy_y[i] = 70*(i-4);
				enemy_x[i] = 700;
 			}
			for(i=0;i<n;i++){
				enemy_alive[i] = 1;
				enemy_move[i] = -10;
			}


		}


		public ActionListener myTimerListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Dimension d;
				d=getSize();
				t+=0.2;
	//-------------------------------------------------------------------
				my_x = (int)(v*v_x*t+start_x);
				my_y = (int)(9.8*t*t/2 - v*v_y*t+start_y);
	//-------------------------------------------------------------------
				if((my_x<0)||(my_x>d.width)||(my_y>d.height)||(my_y<0)){
					timer.stop();
					my_x=init_x;
					my_y=init_y;
					t=0.0;
				}

	//-------------------------------------------------------------------
			//check hit
	//-------------------------------------------------------------------
				for(int i=0;i<n;i++){
					if(enemy_alive[i] == 1){
						if( ((my_x + my_width) > enemy_x[i] ) && (my_x < enemy_x[i] + enemy_width) && ( (my_y + my_height) > enemy_y[i]) && (my_y < (enemy_y[i] + enemy_height))){
							enemy_alive[i] = 0;
							num_of_alive --;
						}
					}
				}
				if(num_of_alive == 0){
					System.out.println("=====Game Clear====");
					System.exit(0);
				}


				grab_flag=0;
				repaint();
			}
		};

		public ActionListener myTimerListener2 = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Dimension dim = getSize();
				for(int i=0;i<n;i++){
					enemy_y[i] += enemy_move[i];
					if( (enemy_y[i]<0) || (enemy_y[i] > (dim.height - enemy_height) ) ){
						enemy_move[i] = -enemy_move[i];
					}
				}
				repaint();
			}
		};

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(image,my_x,my_y,this);
			for(int i=0;i<n;i++){
				if(enemy_alive[i] == 1){
					g.drawImage(image2,enemy_x[i],enemy_y[i],this);
				}
			}
			g.setColor(Color.black);
			g.fillRect(95+my_width/2,400,10,100);
			if(grab_flag==1){
				g.drawLine(95+my_width/2,400,mouse_x,mouse_y);
			}
		}

		public void actionPerformed(ActionEvent e){

		}


		public void mouseClicked(MouseEvent me)
		{
		}

		public void mousePressed(MouseEvent me)
		{
			mouse_x = me.getX();
			mouse_y = me.getY();
			if((grab_flag==0)&&(my_x<mouse_x)&&(mouse_x<my_x+my_width)&&(my_y<mouse_y)&&(mouse_y<my_y+my_height)){
				grab_flag = 1;
				start_x = mouse_x;
				start_y = mouse_y;
			}
		}

		public void mouseReleased(MouseEvent me)
		{
			if(grab_flag==1){
				timer = new Timer(50, myTimerListener);
				timer.start();
//-------------------------------------------------------------------
				v_x = (double)(start_x-mouse_x)/100;
				v_y = -(double)(start_y-mouse_y)/100;
//-------------------------------------------------------------------
				start_x = my_x;
				start_y = my_y;
			}
		}

		public void mouseExited(MouseEvent me)
		{
		}

		public void mouseEntered(MouseEvent me)
		{
		}

		public void mouseMoved(MouseEvent me)
		{
		}

		public void mouseDragged(MouseEvent me)
		{
			if(grab_flag==1){
				mouse_x = me.getX();
				mouse_y = me.getY();
				my_x = init_x - (start_x-mouse_x);
				my_y = init_y - (start_y-mouse_y);
				repaint();
			}
		}
	}
}
