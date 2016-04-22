package serie11.model;

import java.io.File;

import javax.swing.text.Document;


/**
 * Le mod�le est une entit� qui maintient une r�f�rence vers un fichier
 *  (File getFile()), une autre vers un document contenant le texte
 *  � afficher (Document getDocument()), et qui indique si le document
 *  et le fichier (lorsqu'ils existent) sont en phase
 *  (boolean isSynchronized()).
 *
 * Le mod�le peut se trouver dans l'un des �tats suivants
 *  (renvoy� par getState()) :
 * <dl>
 * <dt> NOF_NOD (NO File, NO Document)
 * <dd>pas de fichier ni de document
 *  (<code>getFile() == null  &&  getDocument() == null</code>)
 * <dt>NOF_DOC (NO File, Document)
 * <dd>pas de fichier et un document
 *  (<code>getFile() == null  &&  getDocument() != null</code>)
 * <dt>FIL_NOD : (FILe, NO Document)
 * <dd>un fichier et pas de document
 *  (<code>getFile() != null  &&  getDocument() == null</code>)
 * <dt>FIL_DOC : (FILe, DOCument)
 * <dd>un fichier et un document
 *  (<code>getFile() != null  &&  getDocument() != null</code>)<br />
 *  Notez que cet �tat est le seul des quatre pour lequel le mod�le
 *   a une chance d'�tre synchronis�.
 * </dl>
 * 
 * @inv <pre>
 *     getDocument() == null 
 *         <==> getState() == NOF_NOD || getState() == FIL_NOD
 *     getFile() == null 
 *         <==> getState() == NOF_NOD || getState() == NOF_DOC
 *     getFile() != null ==>
 *         getFile().isFile()
 *         getFile().canRead()
 *         getFile().canWrite()
 *     isSynchronized() ==> getState() == FIL_DOC </pre>
 */
public interface PetModel extends ObservableModel {
    
    // REQUETES

    /**
     * Le document associ� � ce mod�le.
     */
    Document getDocument();

    /**
     * Le fichier associ� � ce mod�le.
     */
    File getFile();

    /**
     * L'�tat du mod�le.
     * Pour plus de d�tails, voir la description pr�liminaire.
     */
    State getState();

    /**
     * Le mod�le est-il synchronis� ?
     * La r�ponse vaut true ssi il existe un document et un fichier
     *  et qu'alors la s�quence de caract�res du fichier et celle du document
     *  sont identiques.
     */
    boolean isSynchronized();
    
    // COMMANDES

    /**
     * Vide le document courant.
     * @pre <pre>
     *     getDocument() != null </pre>
     * @post <pre>
     *     getDocument().getLength() == 0
     *     !isSynchronized() </pre>
     */
    void clearDoc() throws PetException;

    /**
     * Supprime du mod�le le document et le fichier courants.
     * @pre <pre>
     *     getDocument() != null </pre>
     * @post <pre>
     *     getFile() == null
     *     getDocument() == null
     *     !isSynchronized() </pre>
     */
    void removeDocAndFile();

    /**
     * Recharge le contenu du fichier courant dans le document courant.
     * @pre <pre>
     *     getState() == State.FIL_DOC
     *     !isSynchronized() </pre>
     * @post <pre>
     *     getDocument() contient le m�me texte que getFile()
     *     isSynchronized() </pre>
     */
    void resetCurrentDocWithCurrentFile() throws PetException;

    /**
     * Sauvegarde le contenu du document courant dans le fichier courant.
     * @pre <pre>
     *     getState() == State.FIL_DOC
     *     !isSynchronized() </pre>
     * @post <pre>
     *     getFile() contient le m�me texte que le document courant
     *     isSynchronized() </pre>
     */
    void saveCurrentDocIntoCurrentFile() throws PetException;

    /**
     * Sauvegarde le contenu du document courant dans le fichier f, qui devient
     *  le fichier courant.
     * @pre <pre>
     *     getDocument() != null
     *     f != null && f.isFile() && f.canRead() && f.canWrite() </pre>
     * @post <pre>
     *     getFile() == f
     *     getFile() contient le m�me texte que le document courant
     *     isSynchronized() </pre>
     */
    void saveCurrentDocIntoFile(File f) throws PetException;

    /**
     * Cr�e un nouveau document � partir du texte contenu dans le fichier f
     *  et m�morise ce dernier dans le mod�le.
     * @pre <pre>
     *     f != null && f.isFile() && f.canRead() && f.canWrite() </pre>
     * @post <pre>
     *     getFile() == f
     *     getDocument() != null
     *     getDocument() contient le m�me texte que le fichier f
     *     isSynchronized() </pre>
     */
    void setNewDocAndNewFile(File f) throws PetException;

    /**
     * Cr�e un nouveau document � partir du texte contenu dans le fichier f
     *  sans m�moriser ce dernier dans le mod�le.
     * @pre <pre>
     *     f != null && f.isFile() && f.canRead() && f.canWrite() </pre>
     * @post <pre>
     *     getFile() == null
     *     getDocument() != null
     *     getDocument() contient le m�me texte que le fichier f
     *     !isSynchronized() </pre>
     */
    void setNewDocFromFile(File f) throws PetException;

    /**
     * Cr�e un nouveau document vide sans fichier associ�.
     * @post <pre>
     *     getFile() == null
     *     getDocument() != null && getDocument().getLength() == 0
     *     !isSynchronized() </pre>
     */
    void setNewDocWithoutFile();
}
