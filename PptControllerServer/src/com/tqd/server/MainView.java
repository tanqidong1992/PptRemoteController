package com.tqd.server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.SocketAddress;

public class MainView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel mDefaultTableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainView() {
		
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton startServer = new JButton("\u542F\u52A8\u670D\u52A1");
		startServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TcpServer.run();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		startServer.setBounds(22, 399, 93, 23);
		contentPane.add(startServer);
		
		JButton stopServer = new JButton("\u505C\u6B62\u670D\u52A1");
		stopServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TcpServer.shutdown();
			}
		});
		stopServer.setBounds(118, 399, 93, 23);
		contentPane.add(stopServer);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 46, 387, 330);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		  mDefaultTableModel=new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ip地址", "连入时间"
				}
			);
		table.setModel(mDefaultTableModel);
		ServerDataManager.setDefaultTableModel(mDefaultTableModel);
		JButton deleteSelected = new JButton("\u65AD\u5F00\u6240\u9009");
		deleteSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				int [] rows=table.getSelectedRows();
				for(int i=0;i<rows.length;i++)
				{
					Object o=mDefaultTableModel.getValueAt(rows[0], 0);
					ServerDataManager.remove((SocketAddress)o);
					
					//table.remove(rows[i]);
				}
				
			}
		});
		deleteSelected.setBounds(301, 399, 93, 23);
		contentPane.add(deleteSelected);
	}
}
