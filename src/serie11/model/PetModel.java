package serie11.model;

import java.io.File;

import javax.swing.text.Document;


/**
 * Le modèle est une entité qui maintient une référence vers un fichier
 *  (File getFile()), une autre vers un document contenant le texte
 *  à afficher (Document getDocument()), et qui indique si le document
 *  et le fichier (lorsqu'ils existent) sont en phase
 *  (boolean isSynchronized()).
 *
 * Le modèle peut se trouver dans l'un des états suivants
 *  (renvoyé par getState()) :
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
 *  Notez que cet état est le seul des quatre pour lequel le modèle
 *   a une chance d'être synchronisé.
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
     * Le document associé à ce modèle.
     */
    Document getDocument();

    /**
     * Le fichier associé à ce modèle.
     */
    File getFile();

    /**
     * L'état du modèle.
     * Pour plus de détails, voir la description préliminaire.
     */
    State getState();

    /**
     * Le modèle est-il synchronisé ?
     * La réponse vaut true ssi il existe un document et un fichier
     *  et qu'alors la séquence de caractères du fichier et celle du document
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
     * Supprime du modèle le document et le fichier courants.
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
     *     getDocument() contient le même texte que getFile()
     *     isSynchronized() </pre>
     */
    void resetCurrentDocWithCurrentFile() throws PetException;

    /**
     * Sauvegarde le contenu du document courant dans le fichier courant.
     * @pre <pre>
     *     getState() == State.FIL_DOC
     *     !isSynchronized() </pre>
     * @post <pre>
     *     getFile() contient le même texte que le document courant
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
     *     getFile() contient le même texte que le document courant
     *     isSynchronized() </pre>
     */
    void saveCurrentDocIntoFile(File f) throws PetException;

    /**
     * Crée un nouveau document à partir du texte contenu dans le fichier f
     *  et mémorise ce dernier dans le modèle.
     * @pre <pre>
     *     f != null && f.isFile() && f.canRead() && f.canWrite() </pre>
     * @post <pre>
     *     getFile() == f
     *     getDocument() != null
     *     getDocument() contient le même texte que le fichier f
     *     isSynchronized() </pre>
     */
    void setNewDocAndNewFile(File f) throws PetException;

    /**
     * Crée un nouveau document à partir du texte contenu dans le fichier f
     *  sans mémoriser ce dernier dans le modèle.
     * @pre <pre>
     *     f != null && f.isFile() && f.canRead() && f.canWrite() </pre>
     * @post <pre>
     *     getFile() == null
     *     getDocument() != null
     *     getDocument() contient le même texte que le fichier f
     *     !isSynchronized() </pre>
     */
    void setNewDocFromFile(File f) throws PetException;

    /**
     * Crée un nouveau document vide sans fichier associé.
     * @post <pre>
     *     getFile() == null
     *     getDocument() != null && getDocument().getLength() == 0
     *     !isSynchronized() </pre>
     */
    void setNewDocWithoutFile();
}
