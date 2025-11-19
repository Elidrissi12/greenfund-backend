@echo off
REM Script batch pour lancer l'analyse SonarQube
REM Usage: run-sonar.bat

set SONAR_TOKEN=sqp_41da5aa48bfc8d008d2c6a01596327a959e14f28

echo Token SonarQube configuré
echo Lancement de l'analyse...

call mvnw.cmd clean verify sonar:sonar

echo.
echo Analyse terminée !
echo Consultez les résultats sur: http://localhost:9000
pause

