Насибуллин Эрик, 11-902, установка:
```
mkdir /tmp/nasibullin && cd /tmp/nasibullin
wget -O CloudPhoto.jar https://github.com/Samurai9/CloudPhoto/raw/main/CloudPhoto.jar
echo 'alias cloudphoto="java -jar /tmp/nasibullin/CloudPhoto.jar"' >> ~/.bashrc
source ~/.bashrc
```
Так же для запуска нужна JRE 17 версии *(проверить можно **java --version**)*:
```sh
sudo apt install openjdk-17-jre
```
