@echo off
:: Configuration minimale
set CONTAINER_NAME=mariadb_container
set MARIADB_ROOT_PASSWORD=root
set MARIADB_DATABASE=breeders_app
set SCRIPT_PATH=..\init.sql

echo Creating MariaDB container...
:: Suppression d'un potentiel conteneur pré-existant
docker rm -f %CONTAINER_NAME% >nul 2>&1
:: Création du conteneur MariaDB
docker run -d ^
  --name %CONTAINER_NAME% ^
  -e MARIADB_ROOT_PASSWORD=%MARIADB_ROOT_PASSWORD% ^
  -e MARIADB_DATABASE=%MARIADB_DATABASE% ^
  -p 3306:3306 ^
  mariadb:latest

echo Waiting for MariaDB to start...

:: Boucle d'attente jusqu'à ce que le conteneur accepte les connexions
:wait_loop
docker exec %CONTAINER_NAME% mariadb -uroot -p%MARIADB_ROOT_PASSWORD% -e "SELECT 1;" >nul 2>&1
if errorlevel 1 (
    timeout /t 2 >nul
    goto wait_loop
)

echo MariaDB is ready. Importing SQL script...
:: Import de la base dans le conteneur
docker exec -i %CONTAINER_NAME% mariadb -uroot -proot %MARIADB_DATABASE% < %SCRIPT_PATH%

echo Done !
