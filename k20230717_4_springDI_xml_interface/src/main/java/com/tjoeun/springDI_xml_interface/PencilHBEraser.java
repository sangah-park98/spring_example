package com.tjoeun.springDI_xml_interface;

public class PencilHBEraser implements Pencil {

	@Override
	public void use() {
		System.out.println("지우개 달린 HB 연필이 글을 씁니다.");
	}

}
