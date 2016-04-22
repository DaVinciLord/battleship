package serie11.model;

import java.io.File;
import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Le modèle est une entité qui maintient une référence vers un fichier
 *  (File getFile()) et une autre vers un document contenant le texte
 *  à afficher (Document getDocument()).
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
 * </dl>
 *
 * Voici les différentes actions autorisées sur le modèle :
 * <ul>
 * <li>d : setDocument(Document d)</li>
 * <li>f : setFile(File f)</li>
 * <li>l : load()</li>
 * <li>rd : removeDocument()</li>
 * <li>rf : removeFile()</li>
 * <li>s : save()</li>
 * </ul>
 *
 * Voici la table des transitions autorisées entre les différents états du
 *  modèle :
 * <pre>
 *        |   d   |   f   |   rd  |   rf  |   l   |   s   |
 * -------+-------+-------+-------+-------+-------+-------+
 * NOF_NOD|NOF_DOC:FIL_NOD:NOF_NOD:NOF_NOD:   -   :   -   |
 * -------+-------+-------+-------+-------+-------+-------+
 * NOF_DOC|NOF_DOC:FIL_DOC:NOF_NOD:NOF_DOC:   -   :   -   |
 * -------+-------+-------+-------+-------+-------+-------+
 * FIL_NOD|FIL_DOC:FIL_NOD:FIL_NOD:NOF_NOD:   -   :   -   |
 * -------+-------+-------+-------+-------+-------+-------+
 * FIL_DOC|FIL_DOC:FIL_DOC:FIL_NOD:NOF_DOC:FIL_DOC:FIL_DOC|
 * -------+-------+-------+-------+-------+-------+-------+</pre>
 * 
 * @inv <pre>
 *     getDocument() == null 
 *         <==> getState() == NOF_NOD || getState() == FIL_NOD
 *     getFile() == null 
 *         <==> getState() == NOF_NOD || getState() == NOF_DOC
 *     getFile() != null ==>
 *         getFile().isFile()
 *         getFile().canRead()
 *         getFile().canWrite() </pre>
 */
interface FilDocManager {
    
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
    
    // COMMANDES
    
    /**
     * Ecrire le fichier dans le document (action l).
     * Crée un nouveau document pour ce modèle, reflétant le contenu du
     *  fichier.
     * @pre <pre>
     *    getState() == FIL_DOC </pre>
     * @post <pre>
     *    le document reflète le contenu du fichier </pre>
     * @throws java.io.FileNotFoundException
     *    Si le fichier n'a pas pu être ouvert en lecture
     * @throws IOException
     *    S'il s'est produit une erreur lors de la lecture du fichier
     * @throws BadLocationException 
     *    S'il s'est produit une erreur interne lors du traitement du document
     */
    void load() throws IOException, BadLocationException;
    
    /**
     * Enlever le document du modèle (action rd).
     * @post <pre>
     *    getDocument() == null </pre>
     */
    void removeDocument();
    
    /**
     * Enlever le fichier du modèle (action rf).
     * @post <pre>
     *    getFile() == null </pre>
     */
    void removeFile();
    
    /**
     * Ecrire le document dans le fichier (action s).
     * @pre <pre>
     *    getState() == FIL_DOC </pre>
     * @post <pre>
     *    le contenu du fichier reflète celui du document </pre>
     * @throws IOException
     *    S'il s'est produit une erreur lors de l'écriture dans le fichier
     * @throws BadLocationException 
     *    S'il s'est produit une erreur interne lors du traitement du document
     */
    void save() throws IOException, BadLocationException;
    
    /**
     * Associer un document au modèle (action d).
     * Les classes d'implémentation devront s'assurer que lorsque ce document
     *  sera modifié, le modèle en sera automatiquement notifié.
     * @pre <pre>
     *     d != null </pre>
     * @post <pre>
     *    getDocument() == d </pre>
     */
    void setDocument(Document d);
    
    /**
     * Fixer le fichier courant (action f).
     * @pre <pre>
     *    f != null && f.isFile() </pre>
     * @post <pre>
     *    getFile() == f </pre>
     */
    void setFile(File f);
}
