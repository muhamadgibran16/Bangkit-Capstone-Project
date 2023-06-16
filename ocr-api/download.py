import gdown

url = "https://drive.google.com/file/d/1Kw0fiqUBaZOaLRqJvEc7p-m39b8KTEK5/view?usp=sharing"
output = "bounding_ktp03.h5"
gdown.download(url, output, quiet=False, fuzzy=True)  