package fr.sigl.imoe.ejb.mdb.tp.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.sigl.imoe.ejb.facade.tp.bean.MessageSenderRemote;
import fr.sigl.imoe.ejb.facade.tp.dto.MessageDTO;

/**
 * Client de l'EJB Session permettant d'envoyer des messages
 * JMS.
 *
 * @author Chris
 */
public class MessageClient {
    /**
     * Logger JUL.
     */
    private static final Logger LOGGER = Logger.getLogger(MessageClient.class.getName());

    /**
     * Méthode de lancement de l'applicatif client.
     * 
     * @param args
     *            Paramètres de l'application.
     */
    public static void main(String[] argv) {
        Context context = null;
        try {
            // Création du contexte JNDI
            context = new InitialContext();
            MessageSenderRemote sender = (MessageSenderRemote)
            		context.lookup("ejb:TpEjbMDBEAR/TpEjbMDB/MessageSender"
            					   + "!fr.sigl.imoe.ejb.facade.tp.bean.MessageSenderRemote");

            // Envoi d'un message de type texte.
            sender.sendSimpleMessage("Un simple message de vérification du fonctionnement.");

            // Envoi d'un premier message de type Object
            sender.sendObjectMessage(new MessageDTO(MessageClient.class.getSimpleName(), "Un message dans une structure DTO"));

            // Envoi d'un second message de type Object
            sender.sendObjectMessage(new MessageDTO(MessageClient.class.getSimpleName(), "Un autre message dans une structure DTO"));
        } catch (NamingException e) {
            LOGGER.log(Level.SEVERE, "Echec d'initialisation du contexte JNDI ou lors de la récupération du bean.", e);
            throw new RuntimeException(e);
        } finally {
            try {
                // Fermeture du contexte JNDI
                if (context != null) {
                    context.close();
                }
            } catch (Exception e) {
            }
        }
    }
}
