##Camilo Arevalo~
##sudo apt-get install python-numpy python-scipy
##sudo pip install librosa
from librosa.core import load as READ
from librosa.output import write_wav as WRITE
from random import randint as RANDOM
import numpy as np
import sys

# songs=['amame.mp3']
# F=open("list_path_songs.txt",'r') #can use ls -1 in linux to list files in directory
song_name = sys.argv[1]
signal,fs= READ(song_name)
cut_point=RANDOM(0,len(signal)-(fs*10))
output_song_name = sys.argv[2]
print(output_song_name)
WRITE(output_song_name,signal[cut_point:cut_point+(fs*10)],fs,False)
