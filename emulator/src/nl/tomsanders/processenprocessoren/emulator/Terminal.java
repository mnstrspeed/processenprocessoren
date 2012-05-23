package nl.tomsanders.processenprocessoren.emulator;

import java.util.Observer;
import java.util.Observable;

import java.awt.Font;
import java.awt.Color;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;

public class Terminal extends JFrame implements Observer, KeyListener,
	   WindowListener {
	private JTextArea textArea;
	private IoBuffer buffer;
	private Processor processor;

	public Terminal(IoBuffer buffer, Processor processor) {
		super("Terminal");
		this.buffer = buffer;
		this.buffer.addObserver(this);
		this.processor = processor;
		this.setSize(600, 400);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.textArea = new JTextArea();
		this.textArea.setLineWrap(true);
		this.textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
		this.textArea.setForeground(Color.white);
		this.textArea.setBackground(Color.black);
		this.textArea.setCaretColor(Color.white);
		DefaultCaret caret = new DefaultCaret();
		caret.setBlinkRate(0);
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		this.textArea.setCaret(caret);
		this.textArea.addKeyListener(this);
		this.addWindowListener(this);
		this.add(new JScrollPane(textArea));
		this.setVisible(true);
	}

	public void update(Observable o, Object arg) {
		char c = this.buffer.pollOut();
		if (c == KeyEvent.VK_BACK_SPACE) {
			int length = this.textArea.getText().length();
			if (length > 0)
				this.textArea.replaceRange("", length - 1, length);
		} else {
			this.textArea.append(c + "");
		}
		this.fixCaret();
	}

	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			shutdown();
		} else {
			this.buffer.addIn(e.getKeyChar());
			this.fixCaret();
		}
	}

	public void windowClosing(WindowEvent e) {
		this.shutdown();
	}

	private void shutdown() {
		this.processor.halt();
		this.setVisible(false);
	}

	private void fixCaret() {
		int position = this.textArea.getText().length();
		this.textArea.setCaretPosition(position);
	}

	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
}
