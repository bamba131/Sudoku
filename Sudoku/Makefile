### VARIABLES ###

JC = javac
JCFLAGS = -encoding UTF-8 -implicit:none

JVM = java
JVMFLAGS = 

### REGLES ESSENTIELLES ###

elaborationGrille.class : elaborationGrille.java Projet1/GrilleSudoku.class Projet1/GenerateurGrille.class Projet1/GestionFichier.class
	${JC} ${JCFLAGS} elaborationGrille.java

Resoudre.class : Resoudre.java Projet1/GrilleSudoku.class Projet1/GestionFichier.class Projet1/Resolution.class
	${JC} ${JCFLAGS} Resoudre.java

Projet1/GrilleSudoku.class : Projet1/GrilleSudoku.java 
	${JC} ${JCFLAGS} Projet1/GrilleSudoku.java

Projet1/Resolution.class : Projet1/Resolution.java 
	${JC} ${JCFLAGS} Projet1/Resolution.java

Projet1/GestionFichier.class : Projet1/GestionFichier.java 
	${JC} ${JCFLAGS} Projet1/GestionFichier.java

Projet1/GenerateurGrille.class : Projet1/GenerateurGrille.java 
	${JC} ${JCFLAGS} Projet1/GenerateurGrille.java
### REGLES OPTIONNELLES ###

run1 : elaborationGrille.class
	${JVM} ${JVMFLAGS} elaborationGrille

run2 : Resoudre.class
	${JVM} ${JVMFLAGS} Resoudre
clean :
	-rm -f *.class
	-rm -f Projet1/*.class

mrproper : clean elaborationGrille.class
mrproper2: clean elaborationGrille.class

### BUTS FACTICES ###

.PHONY : run clean mrproper

### FIN ###