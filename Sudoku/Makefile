### VARIABLES ###

JC = javac
JCFLAGS = -encoding UTF-8 -implicit:none

JVM = java
JVMFLAGS = 

### REGLES ESSENTIELLES ###

elaborationGrille.class : elaborationGrille.java GrilleSudoku.class GenerateurGrille.class GestionFichier.class
	${JC} ${JCFLAGS} elaborationGrille.java

Resoudre.class : Resoudre.java GrilleSudoku.class GestionFichier.class Resolution.class
	${JC} ${JCFLAGS} Resoudre.java

GrilleSudoku.class : GrilleSudoku.java 
	${JC} ${JCFLAGS} GrilleSudoku.java

Resolution.class : Resolution.java 
	${JC} ${JCFLAGS} Resolution.java

GestionFichier.class : GestionFichier.java 
	${JC} ${JCFLAGS} GestionFichier.java

GenerateurGrille.class : GenerateurGrille.java 
	${JC} ${JCFLAGS} GenerateurGrille.java
### REGLES OPTIONNELLES ###

run1 : elaborationGrille.class
	${JVM} ${JVMFLAGS} elaborationGrille

run2 : Resoudre.class
	${JVM} ${JVMFLAGS} Resoudre
clean :
	-rm -f *.class

mrproper : clean elaborationGrille.class
mrproper2: clean elaborationGrille.class

### BUTS FACTICES ###

.PHONY : run clean mrproper

### FIN ###