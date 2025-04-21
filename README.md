# ⚽ Offside Detection from a Single Image

This Java-based application automatically analyzes an **image representing the positions of all players on a football field** to detect **offside situations**, following official football rules.

## 🧠 Key Features

- **Image Analysis**  
  Processes a 2D image or schematic view showing the positions of players and the ball. The algorithm extracts their coordinates on the field.

- **Team Identification**  
  Automatically detects and classifies players by team using colors, tags, or other visual markers.

- **Second-Last Defender Detection**  
  Identifies the last outfield defender (excluding the goalkeeper), a key element for offside rule evaluation.

- **Offside Evaluation**  
  Determines if attacking players are in an offside position at the time of a pass, taking the ball and defender positions into account.

- **Visual Output**  
  Generates an annotated image with offside lines and clear indicators showing which players are offside.

## 🛠️ Technologies Used

- **Computer Vision**: OpenCV  
- **Backend**: Java  
- **Frontend (Optional)**: Java Swing

## 🎯 Objective

Provide a fast, visual, and reliable tool to detect offside situations — useful for:
- Educational purposes  
- Tactical and game analysis  
- Support for refereeing and VAR-like systems