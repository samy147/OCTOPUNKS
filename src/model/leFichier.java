package src.model;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import src.files.File;
import src.files.Pile;
import src.files.TableauDynamique;
import src.files.TypeFichier;

import src.view.Jeu;


public class leFichier
{
    private int id; //200 par exemple dans le premier niveau de exapunks
    private TypeFichier<Integer> fichier; //le type de fichier
    private Coordonnees position; //le fichier aussi a une position

    private ImageIcon imageFichier; // l'image du fichier
    protected JLabel fichierLabel; // le label qui permet d'afficher l'image

    private Jeu jeu;

    /**
     * CONSTRUCTEUR
     * @param id l'identifiant du fichier
     * @param laPosition la position du fichier
     * @param type le type du fichier
     * @throws IOException 
     */
    public leFichier(int id, Coordonnees laPosition) throws IOException
    {
        if(id != 0) {
            this.id = id;
        }
        else {
            throw new IllegalArgumentException("file id is null");
        }

        this.position = laPosition;
        occuperChamp(laPosition);
        laPosition.getSalle().setTheFile(this);

        this.imageFichier = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/image_fichier.png")));
        this.fichierLabel = new JLabel(this.imageFichier);
    }

    public void afficher()
    {
        this.fichierLabel.setSize(20,20);
        switch(position.getSalle().getId())
        {
            case 1 :    setPositionLabel(jeu.getZoneMonde().getCoordonneesGraphiquesSalle1(this.getPosition().getX(), this.getPosition().getY()));
                        break;
            case 2 :    setPositionLabel(jeu.getZoneMonde().getCoordonneesGraphiquesSalle2(this.getPosition().getX(), this.getPosition().getY()));
                        break;
            case 3 :    setPositionLabel(jeu.getZoneMonde().getCoordonneesGraphiquesSalle3(this.getPosition().getX(), this.getPosition().getY()));
                        break;
        }
    }

    /**
     * Permet de définir l'attribut jeu sans modifier le consructeur.
     * @param jeu l'élément à affecter
     */
    public void defineJeu(Jeu jeu)
    {
        if(jeu == null)
        {
            throw new NullPointerException("jeu est null.");
        }
        this.jeu = jeu;
    }

    /**
     * Permet de retirer l'image du fichier de l'interface graphique.
     */
    public void enleverGraphique()
    {
        this.fichierLabel.setIcon(null);
    }

    /**
     * Renvoie le fichier.
     * @return la position u fichier
     */
    public TypeFichier<Integer> getFichier() {
        return this.fichier;
    }
    
    /**
     * Renvoi l'identifiant du fichier.
     * @return l'identifiant du fichier
     */
    public int getId() {
        return id;
    }

    /**
     * @return le label de l'image du fichier
     */
    public JLabel getFichierLabel()
    {
        return this.fichierLabel;
    }

    /**
     * Renvoie la position du fichier.
     * @return la position du fichier
     */
    public Coordonnees getPosition() {
        return this.position;
    }

    /**
     * Permet de libérer
     */
    public void libererChamp(){
        position.getSalle().libererChamp(position);
    }

    public void occuperChamp(Coordonnees laPosition){
        laPosition.getSalle().occuperChamp(laPosition, TypeCellule.FICHIER);
    }

    /**
     * Permet d'affecter un fichier spécifié.
     * @param fichier le fichier à affecter.
     */
    public void setFichier(TypeFichier<Integer> fichier) {
        this.fichier = fichier;
    }

    public void setFileOfSalle(){
        getPosition().getSalle().setTheFile(this);
    }

    /**
     * Permet d'affecter un nouvel identifiant.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Permet de modifier la position
     * @param newPosition la nouvelle position
     */
    public void setPosition(Coordonnees newPosition) {
        this.position.getSalle().libererChamp(this.position); //liberer l'ancienne position
        this.position = newPosition;
        occuperChamp(newPosition); //occuper la novelle position
    }

    /**
     * Permet de modifier la position de l'image du robot (le label sur l'interface graphique).
     * @param xGraphique la nouvelle position graphique x de l'image
     * @param yGraphique la nouvelle position graphique y de l'image 
     */
    public void setPositionLabel(Coordonnees coordonnees)
    {
        this.fichierLabel.setLocation(coordonnees.getXGraphique(), coordonnees.getYGraphique());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof leFichier)) return false;
        leFichier leFichier = (leFichier) o;
        return id == leFichier.id && Objects.equals(fichier, leFichier.fichier) && Objects.equals(position, leFichier.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fichier, position);
    }
}
