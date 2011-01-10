from org.sikuli.guide import ScreenAnnotator
from org.sikuli.script import UnionScreen
from java.awt import Point

s = UnionScreen()
_sa = ScreenAnnotator(s);

def addHighlight(target):
	r = s.getRegionFromPSRM(target)
	_sa.addHighlight(r)

def addText(target, text):
	r = s.getRegionFromPSRM(target)
	_sa.addHighlight(r)
	_sa.addText(text, Point(r.x, r.y+r.h+5))

def addTooltip(target, text):
	r = s.getRegionFromPSRM(target)
	_sa.addHighlight(r)
	_sa.addToolTip(text, Point(r.x, r.y+r.h+5))

def show(secs):
	_sa.show(secs)

def nextStep(text):
	_sa.showWaitForButtonClick("Next", text)


