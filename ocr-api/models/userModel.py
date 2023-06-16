from sqlalchemy import Column, String, Integer, Date, Boolean, Text
from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

class User(db.Model):
    __tablename__ = 'users'

    id = db.Column(db.Integer, primary_key=True)
    uid = db.Column(db.String(100), unique=True)
    name = db.Column(db.String(100), unique=False, nullable=False)
    email = db.Column(db.String(100), unique=False, nullable=False)
    telp = db.Column(db.String(15), unique=False, nullable=False)
    password = db.Column(db.String(100), nullable=False)
    nik = db.Column(db.String(16), unique=False, nullable=False)
    alamat = db.Column(db.String(255), nullable=False)
    ttl = db.Column(db.String(100), nullable=False)
    gol_darah = db.Column(db.String(2), nullable=False)
    rhesus = db.Column(db.String(1), nullable=False)
    gender = db.Column(db.String(100), nullable=False)
    last_donor = db.Column(db.String(100), nullable=False)
    photo = db.Column(db.String(255), nullable=False)
    refresh_token = db.Column(db.String(255), nullable=False)
    verified = db.Column(db.Boolean, nullable=False, default=False)
    ktp = db.Column(db.Boolean, nullable=False, default=False)

    def __init__(
        self, uid=None, name=None, email=None, telp=None, password=None, nik=None, alamat=None,
        ttl=None, gol_darah=None, rhesus=None, gender=None, last_donor=None, photo=None,
        refresh_token=None, verified=False, ktp=False
    ):
        self.uid = uid
        self.name = name
        self.email = email
        self.telp = telp
        self.password = password
        self.nik = nik
        self.alamat = alamat
        self.ttl = ttl
        self.gol_darah = gol_darah
        self.rhesus = rhesus
        self.gender = gender
        self.last_donor = last_donor
        self.photo = photo
        self.refresh_token = refresh_token
        self.verified = verified
        self.ktp = ktp
