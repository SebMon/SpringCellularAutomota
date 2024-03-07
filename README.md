# Cellular Automota using Java Spring
I built this project early in my education as I was learning about topics like dependency injection and componentization. Cellular Automota has always intrigued me and I wanted to try my hand with some simulations, inspired by various videos on the [Game of Life]([https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life](https://www.youtube.com/watch?v=R9Plq-D1gEk)) and a specific [Sebastian Lague video](https://www.youtube.com/watch?v=kzwT3wQWAHE) which lead me to a tutorial on [Reaction Diffusion](https://www.karlsims.com/rd.html) by Karl Sims. I played around with the project for a few days and abandoned it, but years later polished it a little bit.

As it is a beginner project, there are many things that could be improved and are missing. Performance is terrible, and there is no UI to change values.

# How to use
The application should be built with Maven using Java 18. There is a Jar available under releases, which includes all dependencies. Execute with Java 18 to be sure everything works properly. When running the application, the following command line arguments can be entered (in the given order):
- Simulation: Either 'game_of_life' or 'reaction_diffusion'. Default is the Game of Life
- Window and game size: 'wWidth wHeight gWidth gHeight'. Default is '400 400 100 100'. Game size cannot exceed window size. Game size should be proportional to window size, I.E. the following will not work '400 600 100 100' but '400 600 100 150' will work. **BE WARNED:** Using a higher game resolution than 100x100 will exponentially reduce the fps down from 40-60. 

Execution of the Jar file could look something like: `java -jar SpringCellularAutomota-1.0.jar reaction_diffusion 800 800 150 150`. 

When the application is running, it is possible to pause the simulation with Spacebar. When the simulation is paused, one can click the canvas to affect the simulation. Unfortunately the Reaction - Diffusion simulation does not show any reaction as it is paused, but cliccking and dragging the mouse will affect it as will be shown when unpaused.

The Reaction Diffusion simulation relies on four parameters which greatly affect how the simulation behaves. Ideally these values should be adjustable as commandline arguments as well, but I did not take the time to implement this. To adjust these and experiment manually, look for the variables `rateA`, `rateB`, `feedRate` and `killRate` in the file `src/main/java/dk/sdu/se4/cellular/ReactionDiffusion/RDProcessor.java`.
