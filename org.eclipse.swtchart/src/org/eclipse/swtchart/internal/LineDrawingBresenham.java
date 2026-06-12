/*******************************************************************************
 * Copyright (c) 2026 SWTChart project.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart.internal;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;

public class LineDrawingBresenham {

	private int pixelSize = 2;
	private int width;
	private int height;
	private int colorIndex = 1;

	public LineDrawingBresenham(int width, int height) {

		this.width = width;
		this.height = height;
	}

	public LineDrawingBresenham(int width, int height, int pixelSize) {

		this.width = width;
		this.height = height;
		this.pixelSize = pixelSize;
	}

	public void setColorIndex(int colorIndex) {

		this.colorIndex = colorIndex;
	}

	public void drawSymbol(ImageData id, int x, int y, int symbolSize) {

		int radius = symbolSize / 2;
		if(y >= (id.height - symbolSize) || y < symbolSize || x >= (id.width - symbolSize) || x < symbolSize) {
			return;
		}

		for(int xpt = x - radius; xpt <= x + radius; xpt++) {
			for(int ypt = y - radius; ypt <= y + radius; ypt++) {
				id.setPixel(xpt, ypt, colorIndex);
			}
		}
	}

	public void drawSymbol(GC g, int x, int y, int symbolSize) {

		int radius = symbolSize / 2;
		if(y >= (height - symbolSize) || y < symbolSize || x >= (x - symbolSize) || x < symbolSize) {
			return;
		}

		for(int xpt = x - radius; xpt <= x + radius; xpt++) {
			for(int ypt = y - radius; ypt <= y + radius; ypt++) {
				g.drawPoint(xpt, ypt);
			}
		}
	}

	public void drawLinePixelated(GC gc, int x1, int y1, int x2, int y2) {

		int delta = 0;
		int scale = pixelSize;

		x1 = x1 / scale;
		x2 = x2 / scale;
		y1 = y1 / scale;
		y2 = y2 / scale;

		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		int dx2 = 2 * dx;
		int dy2 = 2 * dy;

		int ix = x1 < x2 ? 1 : -1;
		int iy = y1 < y2 ? 1 : -1;

		int x = x1;
		int y = y1;

		if(dx >= dy) {
			while(true) {
				drawSymbol(gc, x * scale, y * scale, pixelSize);
				if(x == x2) {
					break;
				}
				x += ix;
				delta += dy2;
				if(delta > dx) {
					y += iy;
					delta -= dx2;
				}
			}
		} else {
			while(true) {
				drawSymbol(gc, x * scale, y * scale, pixelSize);
				if(y == y2) {
					break;
				}
				y += iy;
				delta += dx2;
				if(delta > dy) {
					x += ix;
					delta -= dy2;
				}
			}
		}
	}

	public void drawLineClipped(GC gc, int x1, int y1, int x2, int y2) {

		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		int ix = x1 < x2 ? 1 : -1;
		int iy = y1 < y2 ? 1 : -1;

		int x;
		int y;
		int xe;
		int ye;
		int xs;
		int ys;

		if(dx == 0) {
			if(iy > 0) {
				ys = y1;
				ye = y2;
			} else {
				ys = y2;
				ye = y1;
			}

			if(ys > height) {
				return;
			}

			if(ys < 0) {
				ys = 0;
			}

			if(ye > height) {
				ye = height;
			}

			y = ys;
			while(true) {
				drawSymbol(gc, x1, y, pixelSize);
				if(y >= ye) {
					break;
				}
				y += pixelSize;
			}

			return;
		}

		if(dy == 0) {
			if(ix > 0) {
				xs = x1;
				xe = x2;
			} else {
				xs = x2;
				xe = x1;
			}

			if(xs > width) {
				return;
			}

			if(xs < 0) {
				xs = 0;
			}

			if(xe > width) {
				xe = width;
			}

			x = xs;
			while(true) {
				drawSymbol(gc, x, y1, pixelSize);
				if(x >= xe) {
					break;
				}
				x += pixelSize;
			}

			return;
		}

		if(dx >= dy) {
			/*
			 * Start Point
			 */
			if(ix > 0) {
				xs = x1;
				xe = x2;
				ys = y1;
				ye = y2;
			} else {
				xs = x2;
				xe = x1;
				ys = y2;
				ye = y1;
			}

			float slopeyx = (ye - ys) / (float)(xe - xs);
			x = xs;
			if(x > width) {
				return;
			}

			if(x < 0) {
				x = 0;
			}

			if(x > width) {
				x = width;
			}

			while(true) {
				y = ys + (int)((x - xs) * slopeyx);
				drawSymbol(gc, x, y, pixelSize);
				if(x >= xe) {
					break;
				}
				x += pixelSize;
			}

		} else {
			/*
			 * Start Point
			 */
			if(iy > 0) {
				ys = y1;
				ye = y2;
				xs = x1;
				xe = x2;
			} else {
				ys = y2;
				ye = y1;
				xs = x2;
				xe = x1;
			}

			float slopexy = (xe - xs) / (float)(ye - ys);
			y = ys;
			if(y > height) {
				return;
			}

			if(y < 0) {
				y = 0;
			}

			if(y > height) {
				y = height;
			}

			while(true) {
				x = xs + (int)((y - ys) * slopexy);
				drawSymbol(gc, x, y, pixelSize);
				if(y >= ye) {
					break;
				}
				y += pixelSize;
			}
		}
	}

	public void drawLinePixelated(ImageData idata, int x1, int y1, int x2, int y2) {

		int delta = 0;
		int scale = pixelSize;

		x1 = x1 / scale;
		x2 = x2 / scale;
		y1 = y1 / scale;
		y2 = y2 / scale;

		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		int dx2 = 2 * dx;
		int dy2 = 2 * dy;

		int ix = x1 < x2 ? 1 : -1;
		int iy = y1 < y2 ? 1 : -1;

		int x = x1;
		int y = y1;
		int xe = x2;
		int ye = y2;

		if(dx >= dy) {
			while(true) {
				drawSymbol(idata, x * scale, y * scale, pixelSize);
				if(x == xe) {
					break;
				}
				x += ix;
				delta += dy2;
				if(delta > dx) {
					y += iy;
					delta -= dx2;
				}
			}
		} else {
			while(true) {
				drawSymbol(idata, x * scale, y * scale, pixelSize);
				if(y == ye) {
					break;
				}
				y += iy;
				delta += dx2;
				if(delta > dy) {
					x += ix;
					delta -= dy2;
				}
			}
		}
	}

	public void drawLineClipped(ImageData id, int x1, int y1, int x2, int y2) {

		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		int ix = x1 < x2 ? 1 : -1;
		int iy = y1 < y2 ? 1 : -1;

		int x;
		int y;
		int xe;
		int ye;
		int xs;
		int ys;

		if(dx == 0) {
			/*
			 * Start Point
			 */
			if(iy > 0) {
				ys = y1;
				ye = y2;
			} else {
				ys = y2;
				ye = y1;
			}

			if(ys > height) {
				return;
			}

			if(ys < 0) {
				ys = 0;
			}

			if(ye > height) {
				ye = height;
			}

			y = ys;
			while(true) {
				drawSymbol(id, x1, y, pixelSize);
				if(y >= ye) {
					break;
				}
				y += pixelSize;
			}

			return;
		}

		if(dy == 0) {
			/*
			 * Start Point
			 */
			if(ix > 0) {
				xs = x1;
				xe = x2;
			} else {
				xs = x2;
				xe = x1;
			}

			if(xs > width) {
				return;
			}

			if(xs < 0) {
				xs = 0;
			}

			if(xe > width) {
				xe = width;
			}

			x = xs;
			while(true) {
				drawSymbol(id, x, y1, pixelSize);
				if(x >= xe) {
					break;
				}
				x += pixelSize;
			}

			return;
		}

		if(dx >= dy) {
			/*
			 * Start Point
			 */
			if(ix > 0) {
				xs = x1;
				xe = x2;
				ys = y1;
				ye = y2;
			} else {
				xs = x2;
				xe = x1;
				ys = y2;
				ye = y1;
			}

			float slopeyx = (ye - ys) / (float)(xe - xs);
			x = xs;
			if(x > width) {
				return;
			}

			if(x < 0) {
				x = 0;
			}

			if(x > width) {
				x = width;
			}

			while(true) {
				y = ys + (int)((x - xs) * slopeyx);
				drawSymbol(id, x, y, pixelSize);
				if(x >= xe) {
					break;
				}
				x += pixelSize;
			}

		} else {
			/*
			 * Start Point
			 */
			if(iy > 0) {
				ys = y1;
				ye = y2;
				xs = x1;
				xe = x2;
			} else {
				ys = y2;
				ye = y1;
				xs = x2;
				xe = x1;
			}

			float slopexy = (xe - xs) / (float)(ye - ys);

			y = ys;
			if(y > height) {
				return;
			}

			if(y < 0) {
				y = 0;
			}

			if(y > height) {
				y = height;
			}

			while(true) {
				x = xs + (int)((y - ys) * slopexy);
				drawSymbol(id, x, y, pixelSize);
				if(y >= ye) {
					break;
				}
				y += pixelSize;
			}
		}
	}

	public void drawLine(ImageData id, int x1, int y1, int x2, int y2) {

		int delta = 0;
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);

		int dx2 = 2 * dx;
		int dy2 = 2 * dy;

		int ix = x1 < x2 ? 1 : -1;
		int iy = y1 < y2 ? 1 : -1;

		int x = x1;
		int y = y1;

		if(dx >= dy) {
			while(true) {
				drawSymbol(id, x, y, pixelSize);
				if(x == x2) {
					break;
				}
				x += ix;
				delta += dy2;
				if(delta > dx) {
					y += iy;
					delta -= dx2;
				}
			}
		} else {
			while(true) {
				drawSymbol(id, x, y, pixelSize);
				if(y == y2) {
					break;
				}
				y += iy;
				delta += dx2;
				if(delta > dy) {
					x += ix;
					delta -= dy2;
				}
			}
		}
	}
}