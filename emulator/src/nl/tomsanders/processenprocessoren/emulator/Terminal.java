package nl.tomsanders.processenprocessoren.emulator;

import java.util.Observer;
import java.util.Observable;

import java.awt.Font;
import java.awt.Color;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;

public class Terminal extends JFrame implements Observer, KeyListener {
	private JTextArea textArea;
	private IoBuffer buffer;

	public Terminal(IoBuffer buffer) {
		super("Terminal");
		this.setSize(600, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		this.add(new JScrollPane(textArea));
		this.buffer = buffer;
		this.buffer.addObserver(this);
		this.setVisible(true);
	}

	public void update(Observable o, Object arg) {
		this.textArea.append(this.buffer.pollOut() + "");
		this.fixCaret();
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE)
			System.exit(0);
		this.buffer.addIn(e.getKeyChar());
		this.fixCaret();
	}

	private void fixCaret() {
		int position = this.textArea.getText().length();
		this.textArea.setCaretPosition(position);
	}
}
