package com.guipalma.api.service;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.guipalma.api.model.MensagemResultado;

@Component
public class ResultadoConsumer {
	
	@RabbitListener(queues = "appResultadoQueue")
    public void listen(MensagemResultado  resultado) {
		exibeMensagemResultadoVotacao(resultado);
	}
	
	private void exibeMensagemResultadoVotacao(final MensagemResultado  resultado){
	    String mensagem = "Pauta: " + resultado.getPauta()  + "\n"+"\n"
	    		+ "Sim: " +resultado.getSim()+"\n"
	    		+ "Não: " +resultado.getNao()+"\n";  		
	    String titulo =  "Votação: "+ resultado.getSessao(); 	    
	    JFrame frame = new JFrame( "frameResultado");	    
	    JOptionPane.showMessageDialog(frame,
	    		mensagem, 
	    		titulo,  
	            JOptionPane.INFORMATION_MESSAGE);
	  }
	}


