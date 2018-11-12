#	The Sail profile file contains information needed to start a default
#	individual sail. Non-default sails should be retrieved from serializations
#	of earlier Sail objects.
#	_________________________________________________________________________
#
#	Default Sail Information Section
#	This section will eventually get cloned several times and placed in a 
#	Sail_Structure.profile type file.  Each clone will represent a piece of 
#	the sail.  Each clone will have an associated monad definition to hold
#	the attitude of the piece.
#
	SailName=TestSail0
	SailMass=1.00
	SailArea=125.00
	Rf=0.95
	Rb=0.05
	Sf=0.99
	Sb=0.10
	Ef=0.05
	Eb=0.95
	Lf=1.00
	Lb=1.00
#
#	How much can related orbit 'constants' change before iterations are
#	considered too big.  This choice controls the coarseness of the 
#	numerical iteration process.
#
	ChangeToleranceL=0.0001
	ChangeToleranceRL=0.0001
#
#	Sail Mission values with which to start the simulation.
#
	SailFOO=Sun
#
	InitialPhaseStep=0.01
	MissionClockStart=0.0
	MissionClockStop=86400.0
#
	SailPositionX=149599650000.0
	SailPositionY=0.0
	SailPositionZ=10000000000.0
#
#
	SailVelocityX=0.0
	SailVelocityY=19786.252875502927
	SailVelocityZ=5000.0
#
	SailAttitudeXY=0.0
	SailAttitudeXZ=1.0
	SailAttitudeYZ=-1.0
#
	SailIntLX=0.01
	SailIntLY=0.0
	SailIntLZ=0.0

#
#	Supporting character files
#
#	StructureFile=Sail_Structure.profile	
	EphemerisFile=Sail.save
#
#	Boolean Flags for controlling details of the simulation for this Sail.
#
	EphemerisSaveON=false
	PerturbationON=true
	RFPerturbationON=true
	HFPerturbationON=false
	FFPerturbationON=false
	NFPerturbationON=false
	MFPerturbationON=false
	IFPerturbationON=false
#
#	End of Profile
