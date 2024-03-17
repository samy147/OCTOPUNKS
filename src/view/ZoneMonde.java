package src.view;

import src.model.Coordonnees;
import src.model.Robot;
import src.model.Salle;
import src.model.TypeCellule;
import src.model.leFichier;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class ZoneMonde extends JPanel
{
    private Jeu jeu;

    protected int widthCellule;
    protected int heightCellule;
    /**
     * L'espace entre chaque cellule
     */
    private int espaceEntreCases;
    protected JLabel caseEntree;

    // INUTILE, MAIS PEUT SERVIR
    private int largeurMonde;
    private int longueurMonde;

    // Contiennent respectivement les coordonnées graphiques des salles 1, 2 et 3.
    protected Coordonnees[][] salle1_coordonnesGraphiques;
    protected Coordonnees[][] salle2_coordonnesGraphiques;
    protected Coordonnees[][] salle3_coordonnesGraphiques;

    /**
     * Contient les coordonnées graphiques de chaque cellule.
     */
    private Coordonnees[][] coordonneesCases;

    /**
     * CONSTRUCTEUR
     * @param octopunks le main
     * @param jeu l'instance de la classe jeu dans laquelle se trouve cette classe
     * @throws IOException si une image est mal lue
     */
    public ZoneMonde(Octopunks octopunks, Jeu jeu)
    {
        this.jeu = jeu;
        this.setLayout(null);

        this.setSize((int)octopunks.getDimension().getWidth()*3/4, (int)octopunks.getDimension().getHeight()*5/6);
        this.setLocation(0,0);
        this.setBackground(Color.BLUE);

        this.coordonneesCases = null; // initialisation dans loadNiveau()
        this.espaceEntreCases = 30;

        setDimensionsMonde();

        loadNiveau(jeu.file);

        setSousTableauxCoordonneesSalles();

    }

    /**
     * Permet d'afficher une cellule
     * @param typeCellule le type de la cellule
     * @param xGraphique la position de la cellule sur l'écran selon l'axe x
     * @param yGraphique la position de la cellule sur l'écran selon l'axe y
     * @throws IOException si l'image de la cellule n'a pas pu être lue
     */
    public void afficherCellule(TypeCellule typeCellule, int xGraphique, int yGraphique, leFichier file) throws IOException
    {
        JLabel image;
        this.widthCellule = 20;
        this.heightCellule = 20;
        switch(typeCellule)
        {
            case ARRIERE :  image = new JLabel(new ImageIcon(ImageIO.read(new File("images/Carre_vert.png"))));
                            image.setSize(widthCellule,heightCellule);
                            image.setLocation(xGraphique,yGraphique);
                            this.add(image);
                            break;

            case ENTREE :   this.caseEntree = new JLabel(new ImageIcon(ImageIO.read(new File("images/Carre_jaune.png"))));
                            caseEntree.setBounds(xGraphique,yGraphique,widthCellule,heightCellule);
                            this.add(caseEntree);
                            break;
            
            
            case ENTRE_DEUX_SALLES :  image = new JLabel(new ImageIcon(ImageIO.read(new File("images/Entree_salle.png"))));
                                image.setSize(widthCellule,heightCellule);
                                image.setLocation(xGraphique,yGraphique);
                                this.add(image);
                                break;

            case EXA1 :     image = jeu.robot1.getRobotLabel();
                            image.setBounds(xGraphique,yGraphique,widthCellule,heightCellule);            
                            this.add(image);

                            image = new JLabel(new ImageIcon(ImageIO.read(new File("images/Vide.png"))));
                            image.setSize(widthCellule,heightCellule);
                            image.setLocation(xGraphique,yGraphique);           
                            this.add(image);
                            break;
            
            case EXA2 :     image = jeu.robot2.getRobotLabel();
                            image.setBounds(xGraphique,yGraphique,widthCellule,heightCellule);            
                            this.add(image);

                            image = new JLabel(new ImageIcon(ImageIO.read(new File("images/Vide.png"))));
                            image.setSize(widthCellule,heightCellule);
                            image.setLocation(xGraphique,yGraphique);           
                            this.add(image);
                            break;

            case FICHIER :  image = file.getFichierLabel();
                            image.setBounds(xGraphique,yGraphique,widthCellule,heightCellule);            
                            this.add(image);

                            image = new JLabel(new ImageIcon(ImageIO.read(new File("images/Vide.png"))));
                            image.setSize(widthCellule,heightCellule);
                            image.setLocation(xGraphique,yGraphique);           
                            this.add(image);
                            break;

            case LINK :     image = new JLabel(new ImageIcon(ImageIO.read(new File("images/Lien.png"))));
                            image.setSize(widthCellule,heightCellule);
                            image.setLocation(xGraphique,yGraphique);
                            this.add(image);                
                            break;
            
            case MUR :      image = new JLabel(new ImageIcon(ImageIO.read(new File("images/mur.png"))));
                            image.setSize(widthCellule,heightCellule);
                            image.setLocation(xGraphique,yGraphique);           
                            this.add(image);                
                            break;
                                

            default :   image = new JLabel(new ImageIcon(ImageIO.read(new File("images/Vide.png"))));
                        image.setSize(widthCellule,heightCellule);
                        image.setLocation(xGraphique,yGraphique);
                        this.add(image);
                        break;
        }
        
    }

    private Coordonnees getCoordonnees(int x, int y)
    {
        if(x < 0 || x > 4)
        {
            throw new IllegalArgumentException();
        }
        if(y < 0 || y > 4)
        {
            throw new IllegalArgumentException();
        }
        return this.coordonneesCases[y][x];
    }

    /**
     * @param x la position x du robot
     * @param y la position y du robot
     * @return les coordonnées graphiques de la salle 1 pour un x et un y donnés
     */
    public Coordonnees getCoordonneesGraphiquesSalle1(int x, int y)
    {
        if(x < 0)
        {
            x = 0;
        } else if(x > 4)
        {
            x = 4;
        }
        if(y < 0)
        {
            y = 0;
        } else if(y > 4)
        {
            y = 4;
        }
        return this.salle1_coordonnesGraphiques[y][x];
    }

    /**
     * @param x la position x du robot
     * @param y la position y du robot
     * @return les coordonnées graphiques de la salle 2 pour un x et un y donnés
     */
    public Coordonnees getCoordonneesGraphiquesSalle2(int x, int y)
    {
        if(x < 0)
        {
            x = 0;
        } else if(x > 4)
        {
            x = 4;
        }
        if(y < 0)
        {
            y = 0;
        } else if(y > 4)
        {
            y = 4;
        }
        return this.salle2_coordonnesGraphiques[y][x];
    }

    /**
     * @param x la position x du robot
     * @param y la position y du robot
     * @return les coordonnées graphiques de la salle 3 pour un x et un y donnés
     */
    public Coordonnees getCoordonneesGraphiquesSalle3(int x, int y)
    {
        if(x < 0)
        {
            x = 0;
        } else if(x > 4)
        {
            x = 4;
        }
        if(y < 0)
        {
            y = 0;
        } else if(y > 4)
        {
            y = 4;
        }
        return this.salle3_coordonnesGraphiques[y][x];
    }

    /**
     * @return la largeur du monde.
     */
    private int getLargeurMonde()
    {
        return this.largeurMonde;
    }

    /**
     * @return la longueur du monde.
     */
    private int getLongueurMonde()
    {
        return this.longueurMonde;
    }

    public void setFile(leFichier file)
    {
        this.jeu.file = file;
    }

    /**
     * Permet d'afficher le niveau dans la page de jeu.
     */
    private void loadNiveau(leFichier file)
    {
        try {
            String fichierJeuTextuel = "levels/Niveau1.txt";
            
            this.coordonneesCases = new Coordonnees[getLongueurMonde()][getLargeurMonde()];

            BufferedReader fichierBuffer = new BufferedReader(new FileReader(fichierJeuTextuel));
            String ligneFichier;

            int xGraphique = 20;
            int yGraphique = 20;

            int ligne = 0;

            int numSalle = 0;
            Salle salle = jeu.salle1;

            int x = 0;
            int y = 0;

            while ((ligneFichier = fichierBuffer.readLine()) != null)
            {
                int colonne = 0;
                x = 0;

                for(colonne = 0; colonne < ligneFichier.length(); colonne++)
                {
                    char symbol = ligneFichier.charAt(colonne);

                    TypeCellule typeCellule = TypeCellule.fromSymbol(symbol);
                        
                    this.coordonneesCases[ligne][colonne] = new Coordonnees(x, y, xGraphique, yGraphique, salle);
                    afficherCellule(typeCellule,xGraphique,yGraphique, file);
                    xGraphique+=espaceEntreCases;
                    x++;
                }
                xGraphique = 20;
                yGraphique += espaceEntreCases;
                
                ligne++;
                y++;
            }
        fichierBuffer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Récupère les dimensions du monde.
     * @throw NullPointerException si on n'arrive pas à créer un nouveau buffer sur le fichier.
     */
    private void setDimensionsMonde()
    {
        try {
            String fichierJeuTextuel = "levels/Niveau1.txt";
            
            BufferedReader fichierBuffer = new BufferedReader(new FileReader(fichierJeuTextuel));
            String ligneFichier;
            
            int nbLignes = 0;
            while ((ligneFichier = fichierBuffer.readLine()) != null)
            {
                nbLignes++;
            }
            fichierBuffer.close();
            this.longueurMonde = nbLignes;
            fichierBuffer = new BufferedReader(new FileReader(fichierJeuTextuel));
            
            if((ligneFichier = fichierBuffer.readLine()) == null)
            {
                fichierBuffer.close();
                throw new NullPointerException("la ligne est null => largeur non initialisée.");
            }

            this.largeurMonde = ligneFichier.length();
            fichierBuffer.close();       
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Permet d'initialiser 3 sous-tableaux afin de pouvoir se déplacer dans chaque
     * salle plus facilement.
     */
    private void setSousTableauxCoordonneesSalles()
    {
        this.salle1_coordonnesGraphiques = new Coordonnees[5][5];
        this.salle2_coordonnesGraphiques = new Coordonnees[5][5];
        this.salle3_coordonnesGraphiques = new Coordonnees[5][5];

        int ligne = 1;
        int colonne = 1;

        // Démarrage à 1 pour mieux comprendre en fonction du mouvement en back-end
        for(ligne = 1; ligne<=5; ligne++)
        {
            for(colonne = 1; colonne<=5; colonne++)
            {
                salle1_coordonnesGraphiques[ligne-1][colonne-1] = coordonneesCases[ligne][colonne];
                salle1_coordonnesGraphiques[ligne-1][colonne-1].setX(colonne);
                salle1_coordonnesGraphiques[ligne-1][colonne-1].setY(ligne);
                
                salle2_coordonnesGraphiques[ligne-1][colonne-1] = coordonneesCases[ligne+8][colonne];
                salle2_coordonnesGraphiques[ligne-1][colonne-1].setX(colonne);
                salle2_coordonnesGraphiques[ligne-1][colonne-1].setY(ligne+8);
                
                salle3_coordonnesGraphiques[ligne-1][colonne-1] = coordonneesCases[ligne+16][colonne];
                salle2_coordonnesGraphiques[ligne-1][colonne-1].setX(colonne);
                salle2_coordonnesGraphiques[ligne-1][colonne-1].setY(ligne+16);
            }
            
        }
    }
}
