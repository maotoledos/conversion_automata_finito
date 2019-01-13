# Conversion Non-Deterministic Finite Automaton to Deterministic Finite Atomaton
## Conversion de Automata Finito No Determinista -AFD- a Automata Finito Determinista
 
Program is build on Java for desktop.
Created on Netbeans IDE.

### Running the app

1. Open folder on your favorite IDE.
2. Compile and run.
3. Click on 'Abrir' to read a txt file in this order:
	- Q : States of Automaton
	- F : Alphabet
	- i : Initial state of NFD
	- A : Acceptance state
	- W : State transition with alphabet or lamda
File structure:
`Q={1,2,3,4}
F={a,b}
i=1
A={2}
W={(1,a,2),(2,a,1),(1,a,3),(3,b,4),(4,e,2),(4,a,4)}`

4. Click on 'Transformar' to transform into a Deterministic Finite Automaton -DFA-
5. Enter a valid or invalid chain (e.g. aaba) in the bottom input and click on 'Validar' to check if it is or not an acceptance chain.

