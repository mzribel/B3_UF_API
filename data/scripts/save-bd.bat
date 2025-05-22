@echo off
:: Configuration
set CONTAINER_NAME=mariadb_container
set MARIADB_ROOT_PASSWORD=root
set MARIADB_DATABASE=breeders_app
set BACKUP_FILE=backup.sql
set BACKUP_FOLDER=backups

echo Accessing MariaDB container...
:: Vérifie l'existence du conteneur
docker inspect %CONTAINER_NAME% >nul 2>&1
if errorlevel 1 (
    echo Le conteneur "%CONTAINER_NAME%" n'existe pas.
    pause
    exit /b 1
)

:: Récupération de la date au format AAAA-MM-JJ
for /f "tokens=1-3 delims=/- " %%a in ("%date%") do (
    set year=%%c
    set month=%%b
    set day=%%a
)
:: Récupération de l’heure système et suppression des caractères interdits (:)
for /f "tokens=1-2 delims= " %%x in ("%time%") do set fulltime=%%x
set fulltime=%fulltime::=-%

:: Ajout d'un zéro devant l'heure si nécessaire
set hour=%fulltime:~0,2%
if "%hour:~0,1%"==" " set hour=0%hour:~1,1%

:: Formatage de l'heure
set timeformatted=%hour%-%fulltime:~3,2%-%fulltime:~6,2%

:: Construction du nom de fichier
set BACKUP_FILE=%year%-%month%-%day%_%timeformatted%_backup.sql

if not exist %BACKUP_FOLDER% (
    echo Creating backup folder...
    mkdir %BACKUP_FOLDER%
)

:: Sauvegarde de la base de données
echo Saving database "%MARIADB_DATABASE%"...
docker run --rm --network container:%CONTAINER_NAME% mariadb:10.11 mysqldump -h127.0.0.1 -uroot -proot %MARIADB_DATABASE% > %BACKUP_FOLDER%\%BACKUP_FILE%

if errorlevel 1 (
    echo Could not save data.
    pause
    exit /b 1
)

echo Database saved in file \%BACKUP_FOLDER%\%BACKUP_FILE% !
echo Done !